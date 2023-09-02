package com.trianglz.mimar.modules.location_bottom_sheet.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trianglz.core.presentation.extensions.checkPermissionAndTakeAction
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.bottom_sheet.BottomSheetRoundedContainerWithButton
import com.trianglz.mimar.common.presentation.extensions.checkIfLocationServicesIsEnabled
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.addresses.ui.AddressesListViewModel
import com.trianglz.mimar.modules.addresses.ui.composables.AddressPaginatedList
import com.trianglz.mimar.modules.addresses_list.presentation.contract.AddressesListEvent
import com.trianglz.mimar.modules.addresses_list.presentation.contract.AddressesListState

@Composable
fun LocationBottomSheet(
    viewModel: AddressesListViewModel = hiltViewModel(),
    addCurrentLocation: Boolean,
    selectedId: () -> Int,
    filterByBranchIdInCart: () -> Boolean? = { null},
    updateDefaultAddress: () -> Boolean = { true },
    addNewAddressButtonClicked: () -> Unit,
    hideLocationBottomSheet: () -> Unit,
    onLocationSelected: (CustomerAddressUIModel) -> Unit = {},
) {


    LaunchedEffect(key1 = true) {
        viewModel.source.apply {
            showHeader = true
            this.filterByBranchIdInCart = filterByBranchIdInCart()
            addCurrentLocationToAddresses = addCurrentLocation
            selectedLocationId = selectedId()
            refreshAll()
        }

    }

    LaunchedEffect(key1 = selectedId()) {
        viewModel.source.apply {
            selectedLocationId = selectedId()
            refreshAll()
        }
    }

    val context = LocalContext.current
    val locationSourceSettingsLauncher: ManagedActivityResultLauncher<Intent, ActivityResult> =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode != Activity.RESULT_OK) {
                context.checkPermissionAndTakeAction(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    context.checkPermissionAndTakeAction(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                    }
                }
            }
        }


    val showAddAddress = remember {
        derivedStateOf {
            !viewModel.source.loadingState.value.failedInitial && !viewModel.source.loadingState.value.isLoadingInitial
        }

    }
    

    val listBottomPadding: @Composable () -> Dp = remember() {
        {
            MaterialTheme.dimens.bottomNavigationHeight + MaterialTheme.dimens.screenGuideDefault
        }
    }

    val onChangeDefaultAddressClicked: (CustomerAddressUIModel) -> Unit = remember {
        {
            if (updateDefaultAddress())
                viewModel.setEvent(AddressesListEvent.ChangeDefaultAddress(it))
            else {
                onLocationSelected(it)
                hideLocationBottomSheet()
            }

        }

    }

    BottomSheetRoundedContainerWithButton(
        primaryButtonText = { R.string.add_new_address },
        showPrimaryButton = { showAddAddress.value },
        onPrimaryButtonClicked = addNewAddressButtonClicked
    ) {

        AddressPaginatedList(
            { viewModel.source },
            listBottomPadding,
            { 0.dp },
            showDeleteBtn = false,
            showEditBtn = false,
            swipeEnabled = { false },
            fillMaxSize = false,
            filterByBranchIdInCart = filterByBranchIdInCart,
            onChangeDefaultAddressClicked = onChangeDefaultAddressClicked,
            stickyTitleBackClicked = hideLocationBottomSheet,
        )
    }


    GeneralObservers<AddressesListState, AddressesListViewModel>(viewModel = viewModel) {
        when (it) {
            AddressesListState.AskForLocationPermission -> {
                context.checkIfLocationServicesIsEnabled(resultLauncher = locationSourceSettingsLauncher) {
                    context.checkPermissionAndTakeAction(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        hideLocationBottomSheet()
                        viewModel.setEvent(AddressesListEvent.LocationPermissionGrantedSuccessfully)
                    }
                }
            }

            is AddressesListState.HideBottomSheet -> {
                hideLocationBottomSheet()
            }
            is AddressesListState.SendLocationToPreviousScreen ->{
                onLocationSelected(it.address)
                hideLocationBottomSheet()
            }
            else -> {}
        }

    }

    HandleLoadingStateObserver(viewModel = viewModel) {}


}

