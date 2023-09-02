package com.trianglz.mimar.modules.discover.presentation

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.trianglz.core.presentation.extensions.checkPermissionAndTakeAction
import com.trianglz.core.presentation.extensions.toActivityAsNewTask
import com.trianglz.core_compose.presentation.composables.BottomSheetContainerLayout
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.presentation.extensions.checkIfLocationServicesIsEnabled
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationActivity
import com.trianglz.mimar.modules.destinations.*
import com.trianglz.mimar.modules.discover.presentation.composables.DiscoverScreenContent
import com.trianglz.mimar.modules.discover.presentation.contract.DiscoverEvent
import com.trianglz.mimar.modules.discover.presentation.contract.DiscoverState
import com.trianglz.mimar.modules.discover.presentation.contract.DiscoverViewState
import com.trianglz.mimar.modules.discover.presentation.model.DiscoverScreenNavArgs
import com.trianglz.mimar.modules.filter.presenation.model.BranchesFilterUIModel
import com.trianglz.mimar.modules.location_bottom_sheet.presentation.LocationBottomSheet
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@MainGraph(start = false) // sets this as the start destination of the default nav graph
@Destination(navArgsDelegate = DiscoverScreenNavArgs::class)
@Composable
fun DiscoverScreen(
    navigator: DestinationsNavigator,
    viewModel: DiscoverViewModel = hiltViewModel(),
    branchesFilterResult: ResultRecipient<FilterScreenDestination, BranchesFilterUIModel>,

    ) {
    //region Status Bar
    val systemUiController = rememberSystemUiController()
    val onDisposeDarkIcons = !isSystemInDarkTheme()
    val statusBarColor = remember(isSystemInDarkTheme()) {
        if (onDisposeDarkIcons) {
            Color.Transparent
        } else
            Color.White
    }

    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
        )

        onDispose {
//            systemUiController.setStatusBarColor(
//                color = statusBarColor,
//                darkIcons = onDisposeDarkIcons
//            )
        }
    }
    //endregion

    val context = LocalContext.current

    val viewStates = remember {
        viewModel.viewStates ?: DiscoverViewState()
    }

    val categoriesList = remember {
        viewStates.categories ?: listOf()
    }

    val selectedCategory = remember {
        viewStates.selectedCategory
    }

    val user = remember {
        viewStates.user
    }

    val locationAvailable by remember {
        derivedStateOf {
            viewStates.location.value != null
        }
    }

    val defaultAddress: CustomerAddressUIModel? = remember(user.value) {
        user.value?.defaultAddress
    }

    val selectedId = remember {
        viewStates.selectedLocationId
    }

    val filterData by remember {
        viewStates.branchesFilter
    }


    val showNetworkError: @Composable (() -> Boolean) = remember(viewStates.networkError) {
        { viewStates.networkError.collectAsState().value }
    }

    val isRefreshing: @Composable (() -> Boolean) = remember(viewStates.isRefreshing) {
        { viewStates.isRefreshing.collectAsState().value }
    }

    val hasFilterData = remember(filterData) {
        viewStates.hasFilterData
    }
    val onSearchClicked = remember {
        {
            viewModel.setEvent(DiscoverEvent.OnSearchClicked)
        }
    }

    val onSwipedToRefreshTriggered = remember {
        {
            viewModel.viewStates?.isRefreshing?.value = false
            viewModel.setEvent(DiscoverEvent.RefreshBranches)
        }
    }

    val refreshAllScreen = remember {
        {
            viewModel.setEvent(DiscoverEvent.RefreshScreen)
        }
    }

    val onRequestLocationClicked = remember {
        {
            viewModel.setEvent(DiscoverEvent.OnRequestLocationClicked)
        }
    }

    val onChangeAddressClicked = remember {
        {
            viewModel.setEvent(DiscoverEvent.OnChangeAddressClicked)
        }
    }

    val onFilterClicked = remember {
        {
            viewModel.setEvent(DiscoverEvent.OnFilterClicked)
        }
    }

    val locationSourceSettingsLauncher: ManagedActivityResultLauncher<Intent, ActivityResult> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode != RESULT_OK) {
            context.checkPermissionAndTakeAction(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION)
            ) {
                viewModel.setEvent(DiscoverEvent.LocationPermissionGrantedSuccessfully)
            }
        }
    }

    branchesFilterResult.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }
            is NavResult.Value -> {
                viewModel.setEvent(DiscoverEvent.ApplyFilter(result.value))
            }
        }
    }
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()


    val addNewAddressClicked = remember {
        {
            viewModel.setEvent(DiscoverEvent.BottomSheetAddNewAddressClicked)
        }
    }

    val closeBottomSheetClicked = remember {
        {
            viewModel.setEvent(DiscoverEvent.CloseBLocationBottomSheetClicked)
        }
    }

    val onCategoryItemClicked: (Int) -> Unit = remember {
        {
            viewModel.setEvent(DiscoverEvent.SelectCategory(it))
        }
    }


    BottomSheetContainerLayout(
        mainScreenContent = {
            DiscoverScreenContent(
                navigator = navigator,
                userModeHandler = viewModel.userModeHandler,
                isRefreshing = isRefreshing,
                showNetworkError = showNetworkError,
                locationAvailable = { locationAvailable },
                categories = { categoriesList },
                source = { viewModel.source },
                selectedCategoryId = {selectedCategory.value?.id ?: -1},
                hasFilterData = { hasFilterData.value },
                onSearchClicked = onSearchClicked,
                refreshBranches = onSwipedToRefreshTriggered,
                refreshAllScreen = refreshAllScreen,
                onRequestLocationClicked = onRequestLocationClicked,
                defaultAddress = { defaultAddress },
                onChangeAddressClicked = onChangeAddressClicked,
                onFilterClicked = onFilterClicked,
                onCategoryClicked = onCategoryItemClicked,
                cartCount = viewModel.cartCount,
                notificationsCount = viewModel.notificationCount,
            )
        },
        bottomSheetContent = {
            LocationBottomSheet(
                addCurrentLocation = true,
                addNewAddressButtonClicked = addNewAddressClicked,
                hideLocationBottomSheet = closeBottomSheetClicked,
                //updateLocationWithCurrentLocation = updateLocationWithCurrentLocation,
                selectedId = { selectedId.value ?: -1}

            )
        },
        sheetState = sheetState
    )



    GeneralObservers<DiscoverState, DiscoverViewModel>(viewModel = viewModel) {
        when (it) {
            is DiscoverState.OpenBranchDetails -> {
                navigator.navigate(BranchDetailsScreenDestination(it.id))
            }
            is DiscoverState.OpenCategoryDetails -> {
                Toast.makeText(context, "Open Category Details", Toast.LENGTH_SHORT).show()
            }
            DiscoverState.OpenSignupScreen -> {
                context.toActivityAsNewTask<AuthenticationActivity>()
            }
            is DiscoverState.OpenSearchScreen -> {
                navigator.navigate(SearchScreenDestination(it.location))
            }

            DiscoverState.AskForLocationPermission -> {
                context.checkIfLocationServicesIsEnabled(resultLauncher = locationSourceSettingsLauncher) {
                    context.checkPermissionAndTakeAction(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION)
                    ) {
                        viewModel.setEvent(DiscoverEvent.LocationPermissionGrantedSuccessfully)
                    }
                }
            }

            is DiscoverState.OpenFilterDialog -> {
                navigator.navigate(FilterScreenDestination(branchesFilter = it.filter))
            }
            DiscoverState.HideLocationBottomSheet -> {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                }
            }
            DiscoverState.OpenAddAddressScreen -> {
                context.checkPermissionAndTakeAction(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION)
                ) {
                    navigator.navigate(
                        MapScreenMainDestination(
                        previousScreenRoute = DiscoverScreenDestination.route)
                    )
                }
            }
            DiscoverState.OpenLocationBottomSheet -> {
                coroutineScope.launch {
                    if (!sheetState.isVisible) sheetState.show()
                }
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel){}

}