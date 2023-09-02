package com.trianglz.mimar.modules.addresses_list.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.OpenResultRecipient
import com.trianglz.core.presentation.extensions.checkPermissionAndTakeAction
import com.trianglz.core.presentation.extensions.toActivityAsNewTaskWithParams
import com.trianglz.core.presentation.helper.showMaterialDialog
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.extensions.checkIfLocationServicesIsEnabled
import com.trianglz.mimar.modules.addresses.ui.AddressesListViewModel
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.addresses_list.presentation.composables.AddressesListScreenContent
import com.trianglz.mimar.modules.addresses_list.presentation.contract.AddressesListEvent
import com.trianglz.mimar.modules.addresses_list.presentation.contract.AddressesListState
import com.trianglz.mimar.modules.destinations.*
import com.trianglz.mimar.modules.home.presentation.HomeActivity
import com.trianglz.mimar.modules.map.presentation.model.MapScreenMode


@Composable
fun AddressesListScreen(
    viewModel: AddressesListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null,
    addAddressScreenResult: OpenResultRecipient<*>,
    fromHome: Boolean = false,
) {

    val context = LocalContext.current

    //region Status Bar
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )

        onDispose {}
    }
    //endregion

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

                        if (fromHome)
                            MapScreenMainDestination(previousScreenRoute = AddressesListMainDestination.route)
                        else
                            MapScreenAuthDestination(previousScreenRoute = AddressesListAuthDestination.route)

                    }
                }
            }
        }

    val onSkipButtonClicked = remember {
        {
            viewModel.setEvent(AddressesListEvent.SkipButtonClicked)
        }
    }

    val addNewAddressClicked: () -> Unit = remember {
        {
            viewModel.setEvent(AddressesListEvent.AddNewAddressClicked)
        }
    }

    val showAddAddress = remember {
        derivedStateOf {
            !viewModel.source.loadingState.value.isLoadingInitial
                    && viewModel.source.getCurrentList().isNotEmpty()
                    && !viewModel.source.loadingState.value.failedInitial
        }

    }

    addAddressScreenResult.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }
            is NavResult.Value -> {
                result.value?.let {
                    viewModel.setEvent(AddressesListEvent.UpdateAddress(it as CustomerAddressUIModel))
                } ?: run {
                    viewModel.setEvent(AddressesListEvent.RefreshScreen)
                }
            }
        }
    }

    val onDeleteAddressItemIconClicked: (Int) -> Unit = remember {
        {
            viewModel.setEvent(AddressesListEvent.DeleteAddressItemIconClicked(it))
        }
    }

    val onEditAddressClicked: (CustomerAddressUIModel) -> Unit = remember {
        {
            viewModel.setEvent(AddressesListEvent.EditAddressClicked(it))
        }
    }

    val onCheckBoxClicked: (CustomerAddressUIModel) -> Unit = remember {
        {
            viewModel.setEvent(AddressesListEvent.AddressCheckboxClicked(it))
        }
    }

    AddressesListScreenContent(
        navigator = navigator,
        userModeHandler = viewModel.userModeHandler,
        { showAddAddress.value },
        { viewModel.source },
        { fromHome },
        onSkipButtonClicked,
        addNewAddressClicked,
        onCheckBoxClicked,
        onEditAddressClicked,
        onDeleteAddressItemIconClicked,
        notificationsCount = viewModel.notificationCount,
        cartCount = viewModel.cartCount
    )

    GeneralObservers<AddressesListState, AddressesListViewModel>(viewModel = viewModel) {
        when (it) {

            AddressesListState.FinishScreen -> {
                navigator?.popBackStack()
            }

            AddressesListState.OpenHome -> {
                context.toActivityAsNewTaskWithParams<HomeActivity>()
            }
            is AddressesListState.OpenEditAddress -> {
                context.checkIfLocationServicesIsEnabled(resultLauncher = locationSourceSettingsLauncher) {
                    context.checkPermissionAndTakeAction(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        navigator?.navigate(
                            if (fromHome)
                                AddAddressScreenMainDestination(
                                    addressInfo = it.address, mode = MapScreenMode.EditAddress,
                                    fromHome = fromHome
                                ) else
                                AddAddressScreenAuthDestination(
                                    addressInfo = it.address, mode = MapScreenMode.EditAddress,
                                    fromHome = fromHome
                                )

                        )
                    }
                }
            }
            AddressesListState.OpenAddAddress -> {

                context.checkIfLocationServicesIsEnabled(resultLauncher = locationSourceSettingsLauncher) {
                    context.checkPermissionAndTakeAction(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        navigator?.navigate(
                            if (fromHome)
                                MapScreenMainDestination(previousScreenRoute = AddressesListMainDestination.route)
                            else
                                MapScreenAuthDestination(previousScreenRoute = AddressesListAuthDestination.route)

                        )
                    }
                }
            }

            is AddressesListState.ShowDeleteAddressDialog -> {
                showMaterialDialog(
                    context,
                    context.getString(R.string.delete_address),
                    context.getString(R.string.are_you_sure_delete_address),
                    context.getString(R.string.delete),
                    context.getString(R.string.no),
                    {
                        viewModel.setEvent(AddressesListEvent.DeleteAddressDialogClicked(it.addressId))
                    },
                    null
                )
            }
            is AddressesListState.ShowChangeDefaultAddressDialog -> {
                showMaterialDialog(
                    context,
                    context.getString(R.string.change_default_address),
                    context.getString(R.string.are_you_sure_change_default_address),
                    context.getString(R.string.yes),
                    context.getString(R.string.no),
                    {
                        viewModel.setEvent(AddressesListEvent.ChangeDefaultAddress(it.address))
                    },
                    null
                )
            }
            else -> {}
        }

    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }


}
