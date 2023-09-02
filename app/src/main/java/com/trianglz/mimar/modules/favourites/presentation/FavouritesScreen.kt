package com.trianglz.mimar.modules.favourites.presentation

import android.Manifest
import android.widget.Toast
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
import com.trianglz.core_compose.presentation.composables.BottomSheetContainerLayout
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.core_compose.presentation.helper.LocalNavController
import com.trianglz.mimar.modules.location_bottom_sheet.presentation.LocationBottomSheet
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.NavGraphs
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.destinations.BranchDetailsScreenDestination
import com.trianglz.mimar.modules.destinations.DiscoverScreenDestination
import com.trianglz.mimar.modules.destinations.FavouritesScreenDestination
import com.trianglz.mimar.modules.destinations.MapScreenMainDestination
import com.trianglz.mimar.modules.favourites.presentation.composables.FavouritesScreenContent
import com.trianglz.mimar.modules.favourites.presentation.contract.FavouritesEvent
import com.trianglz.mimar.modules.favourites.presentation.contract.FavouritesState
import com.trianglz.mimar.modules.favourites.presentation.contract.FavouritesViewState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@MainGraph(start = false)
@Destination
@Composable
fun FavouritesScreen(
    navigator: DestinationsNavigator,
    viewModel: FavouritesViewModel = hiltViewModel(),) {

    val context = LocalContext.current


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
//                darkIcons = false
//            )
        }
    }
    //endregion


    val viewStates = remember {
        viewModel.viewStates ?: FavouritesViewState()
    }


    val user = remember {
        viewStates.user
    }


    val defaultAddress: CustomerAddressUIModel? = remember(user.value) {
        user.value?.defaultAddress
    }

    val selectedId = remember {
        viewStates.selectedLocationId
    }


    val showNetworkError = viewStates.networkError.collectAsState().value



    val refreshScreen = remember {
        {
            viewModel.setEvent(FavouritesEvent.RefreshAllScreen)
        }
    }

    val refreshBranches = remember {
        {
            viewModel.setEvent(FavouritesEvent.RefreshBranches)
        }
    }


    val onDiscoverBranchesClicked = remember {
        {
            viewModel.setEvent(FavouritesEvent.OnDiscoverClicked)
        }
    }

    val onChangeAddressClicked = remember {
        {
            viewModel.setEvent(FavouritesEvent.OnChangeAddressClicked)
        }
    }

    val addNewAddressClicked = remember {
        {
            viewModel.setEvent(FavouritesEvent.BottomSheetAddNewAddressClicked)
        }
    }

    val closeBottomSheetClicked = remember {
        {
            viewModel.setEvent(FavouritesEvent.CloseLocationBottomSheetClicked)
        }
    }


    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()


    BottomSheetContainerLayout(
        mainScreenContent = {
            FavouritesScreenContent(
                navigator,
                userModeHandler = viewModel.userModeHandler,
                { showNetworkError },
                { viewModel.source },
                refreshBranches,
                refreshScreen,
                { defaultAddress },
                onDiscoverBranchesClicked,
                onChangeAddressClicked,
                cartCount = viewModel.cartCount,
                notificationsCount = viewModel.notificationCount,
            )
        },
        bottomSheetContent = {
            LocationBottomSheet(
                addCurrentLocation = true,
                addNewAddressButtonClicked = addNewAddressClicked,
                hideLocationBottomSheet = closeBottomSheetClicked,
                selectedId = { selectedId.value ?: -1},
            )
        },
        sheetState = sheetState
    )



    val navController = LocalNavController.current

    GeneralObservers<FavouritesState, FavouritesViewModel>(viewModel = viewModel) {
        when (it) {

            is FavouritesState.OpenBranchDetails ->{
                navigator.navigate(BranchDetailsScreenDestination(it.id))
            }
            FavouritesState.OpenDiscoverScreen -> {
                navController.navigate(DiscoverScreenDestination.route) {
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
                    navigator.navigate(DiscoverScreenDestination.route)
                }
            }

            FavouritesState.HideLocationBottomSheet -> {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                }
            }
            FavouritesState.OpenAddAddressScreen -> {
                context.checkPermissionAndTakeAction(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION)
                ) {
                    navigator.navigate(
                        MapScreenMainDestination(previousScreenRoute = FavouritesScreenDestination.route)
                    )
                }
            }
            FavouritesState.OpenLocationBottomSheet -> {
                coroutineScope.launch {
                    if (!sheetState.isVisible) sheetState.show()
                }
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel){}

}