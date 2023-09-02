package com.trianglz.mimar.modules.user_home.presentation

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
import com.ramcosta.composedestinations.navigation.popUpTo
import com.trianglz.core.presentation.extensions.checkPermissionAndTakeAction
import com.trianglz.core.presentation.extensions.toActivityAsNewTask
import com.trianglz.core_compose.presentation.composables.BottomSheetContainerLayout
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.core_compose.presentation.helper.LocalNavController
import com.trianglz.mimar.modules.location_bottom_sheet.presentation.LocationBottomSheet
import com.trianglz.mimar.common.presentation.extensions.checkIfLocationServicesIsEnabled
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.NavGraphs
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsScreenMode
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationActivity
import com.trianglz.mimar.modules.destinations.*
import com.trianglz.mimar.modules.discover.presentation.contract.DiscoverEvent
import com.trianglz.mimar.modules.user_home.presentation.composables.UserHomeScreenContent
import com.trianglz.mimar.modules.user_home.presentation.contract.UserHomeEvent
import com.trianglz.mimar.modules.user_home.presentation.contract.UserHomeState
import com.trianglz.mimar.modules.user_home.presentation.contract.UserHomeViewState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@MainGraph(start = true) // sets this as the start destination of the default nav graph
@Destination
@Composable
fun UserHomeScreen(
    navigator: DestinationsNavigator,
    viewModel: UserHomeViewModel = hiltViewModel(),
) {
    //region Status Bar
    val systemUiController = rememberSystemUiController()
//    val onDisposeDarkIcons = !isSystemInDarkTheme()
//    val statusBarColor = remember(isSystemInDarkTheme()) {
//        if (onDisposeDarkIcons) {
//            Color.Transparent
//        } else
//            Color.White
//    }


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


    val navController = LocalNavController.current

    val context = LocalContext.current

    val viewStates = remember {
        viewModel.viewStates ?: UserHomeViewState()
    }

    val currentList = remember {
        viewStates.list
    }

    val user = remember {
        viewStates.user
    }

    val locationEnabled by remember {
        derivedStateOf {
            viewStates.location.value != null || viewStates.popularBranches.value?.showShimmer == true
        }
    }

    val defaultAddress: CustomerAddressUIModel? = remember(user.value) {
        user.value?.defaultAddress
    }

    val showNetworkError: @Composable (() -> Boolean) = remember(viewStates.networkError) {
        { viewStates.networkError.collectAsState().value }
    }

    val isRefreshing: @Composable (() -> Boolean) = remember(viewStates.isRefreshing) {
        { viewStates.isRefreshing.collectAsState().value }
    }

    val onSearchClicked = remember {
        {
            viewModel.setEvent(UserHomeEvent.OnSearchClicked)
        }
    }

    val onSwipedToRefreshTriggered = remember {
        {
            viewModel.viewStates?.isRefreshing?.value = false
            viewModel.setEvent(UserHomeEvent.RefreshScreen)
        }
    }

    val onGetStartedClicked = remember {
        {
            viewModel.setEvent(UserHomeEvent.OnGetStartedClicked)
        }
    }

    val onRequestLocationClicked = remember {
        {
            viewModel.setEvent(UserHomeEvent.OnRequestLocationClicked)
        }
    }

    val onChangeAddressClicked = remember {
        {
            viewModel.setEvent(UserHomeEvent.OnChangeAddressClicked)
        }
    }


    val locationSourceSettingsLauncher: ManagedActivityResultLauncher<Intent, ActivityResult> = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode != RESULT_OK) {
            context.checkPermissionAndTakeAction(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION)
            ) {
                viewModel.setEvent(UserHomeEvent.LocationPermissionGrantedSuccessfully)
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )


    val addNewAddressClicked = remember {
        {
            viewModel.setEvent(UserHomeEvent.BottomSheetAddNewAddressClicked)
        }
    }

    val closeLocationBottomSheetClicked = remember {
        {
            viewModel.setEvent(UserHomeEvent.CloseLocationBottomSheetClicked)
        }
    }

    val onCategoryItemClicked: (Int) -> Unit = remember {
        {
            viewModel.setEvent(UserHomeEvent.ItemCategoryClicked(it))
        }
    }

    val coroutineScope = rememberCoroutineScope()


    BottomSheetContainerLayout(
        mainScreenContent = {
            UserHomeScreenContent(
                navigator = navigator,
                userModeHandler = viewModel.userModeHandler,
                isRefreshing = isRefreshing,
                showNetworkError = showNetworkError,
                locationEnabled = { locationEnabled },
                currentList = { currentList },
                onSearchClicked = onSearchClicked,
                onGetStartedClicked = onGetStartedClicked,
                onSwipedToRefresh = onSwipedToRefreshTriggered,
                onRequestLocationClicked = onRequestLocationClicked,
                defaultAddress = { defaultAddress },
                onChangeAddressClicked = onChangeAddressClicked,
                onCategoryItemClicked = onCategoryItemClicked,
                cartCount = viewModel.cartCount,
                notificationsCount = viewModel.notificationCount,
            )
        },
        bottomSheetContent = {
            LocationBottomSheet(
                addCurrentLocation = true,
                addNewAddressButtonClicked = addNewAddressClicked,
                hideLocationBottomSheet = closeLocationBottomSheetClicked,
                selectedId = { viewStates.selectedLocationId.value ?: -1}
//                onLocationSelected = {
//                    closeBottomSheetClicked()
//                }
            )
        },
        sheetState = sheetState
    )

    GeneralObservers<UserHomeState, UserHomeViewModel>(viewModel = viewModel) {
        when (it) {
            UserHomeState.OpenAllCategories -> {
                navigator.navigate(CategoriesScreenDestination())
            }
            UserHomeState.OpenAllFavorites -> {
                navController.navigate(FavouritesScreenDestination.route) {
                    // Pop up to the root of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(NavGraphs.mainGraph) {
                        saveState = true
                    }

                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                if (navController.currentDestination?.route != FavouritesScreenDestination.route) {
                    navigator.navigate(FavouritesScreenDestination.route)
                }
            }
            UserHomeState.OpenAllPopularProviders -> {
                navigator.navigate(DiscoverScreenDestination(categoryId = 0)){

                    // Pop up to the root of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(NavGraphs.mainGraph) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

                if (navController.currentDestination?.route != DiscoverScreenDestination.route) {

                    navigator.navigate(DiscoverScreenDestination(categoryId = 0)){
                        popUpTo(NavGraphs.mainGraph)
                    }
                }
            }
            is UserHomeState.OpenAppointmentDetails -> {
                navigator.navigate(AppointmentDetailsScreenDestination(it.id, mode = AppointmentDetailsScreenMode.AppointmentDetails), builder = {launchSingleTop = true})
            }
            is UserHomeState.OpenBranchDetails -> {
                navigator.navigate(BranchDetailsScreenDestination(it.id))
            }
            is UserHomeState.OpenCategoryDetails -> {

                navigator.navigate(DiscoverScreenDestination(categoryId = it.id)){

                    // Pop up to the root of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(NavGraphs.mainGraph) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

                navigator.navigate(DiscoverScreenDestination(categoryId =it.id)){
                    popUpTo(NavGraphs.mainGraph)
                }
            }
            UserHomeState.OpenSignupScreen -> {
                context.toActivityAsNewTask<AuthenticationActivity>()
            }
            is UserHomeState.OpenSearchScreen -> {
                navigator.navigate(SearchScreenDestination(it.location))

            }
            UserHomeState.AskForLocationPermission -> {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                }
                context.checkIfLocationServicesIsEnabled(resultLauncher = locationSourceSettingsLauncher) {
                    context.checkPermissionAndTakeAction(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION)
                    ) {
                        viewModel.setEvent(UserHomeEvent.LocationPermissionGrantedSuccessfully)
                    }
                }
            }

            UserHomeState.OpenLocationBottomSheet -> {
                coroutineScope.launch {
                    if (!sheetState.isVisible) sheetState.show()
                }
            }
            UserHomeState.HideLocationBottomSheet -> {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                }
            }

            UserHomeState.OpenAddAddressScreen -> {
                context.checkPermissionAndTakeAction(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION)
                ) {
                    navigator.navigate(MapScreenMainDestination(
                        previousScreenRoute = UserHomeScreenDestination.route))
                }
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel){}

}