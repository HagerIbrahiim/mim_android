package com.trianglz.mimar.modules.branch_details.presentation

import androidx.activity.compose.BackHandler
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
import com.trianglz.core.presentation.extensions.toast
import com.trianglz.core_compose.presentation.composables.BottomSheetContainerLayout
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.presentation.compose_views.rememberCollapsingToolbarScaffoldState
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.branch_details.presentation.composables.BranchDetailsScreenContent
import com.trianglz.mimar.modules.offered_location.presentation.composables.OfferedLocationBottomSheet
import com.trianglz.mimar.modules.branch_details.presentation.contract.BranchDetailsEvent
import com.trianglz.mimar.modules.branch_details.presentation.contract.BranchDetailsState
import com.trianglz.mimar.modules.branch_details.presentation.contract.BranchDetailsViewState
import com.trianglz.mimar.modules.branch_details.presentation.model.BranchDetailsNavArgs
import com.trianglz.mimar.modules.branches.presentation.model.BranchDetailsUIModel
import com.trianglz.mimar.modules.destinations.BranchReviewsScreenDestination
import com.trianglz.mimar.modules.destinations.BranchViewAllScreenDestination
import com.trianglz.mimar.modules.destinations.CartScreenDestination
import com.trianglz.mimar.modules.destinations.NotificationsListScreenDestination
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@MainGraph
@Composable
@Destination(navArgsDelegate = BranchDetailsNavArgs::class)
fun BranchDetailsScreen(
    viewModel: BranchDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null,

    ) {

    val context = LocalContext.current


    val state = rememberCollapsingToolbarScaffoldState()

    val toolbarProgress = remember(state.toolbarState.progress){
        state.toolbarState.progress
    }

    val viewStates = remember {
        viewModel.viewStates ?: BranchDetailsViewState()
    }


    val isSwipeEnabled = remember(toolbarProgress) {
        derivedStateOf {
            toolbarProgress == 1f
        }
    }

    val specialities = remember {
        viewStates.specialtiesList
    }

    val branch = remember {
        viewStates.branchDetails
    }

    val isBackToHome = remember {
        viewStates.isBackToHome
    }

    val filteredOfferedLocation by remember {
        viewStates.filteredOfferedLocation
    }

    val updateFilterData = remember {
        viewStates.updateFilterData
    }

    val showNetworkError = viewStates.networkError.collectAsState()


    val isRefreshing = viewStates.isRefreshing.collectAsState()


    val selectedSpecialityId = remember {
        viewStates.selectedSpecialty.value?.id ?: -1
    }

    val showFilterIcon = remember(branch.value) {
        branch.value?.offeredLocation is OfferedLocationType.Both
    }

    val hasFilteredData = remember(filteredOfferedLocation) {
        filteredOfferedLocation != null
    }

    val onRefreshAllScreen = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.RefreshScreen)
        }
    }

    val onRefreshServices = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.RefreshServices)
        }
    }

    val onFilterClicked = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.FilterClicked)
        }
    }

    val onAllInfoClicked = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.AllInfoClicked)
        }
    }

    val onAllReviewsClicked = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.AllReviewsClicked)
        }
    }

    val onCartClicked = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.CartClicked)
        }
    }

    val onBackClicked = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.BackClicked)
        }
    }

    val onNotificationsListClicked = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.NotificationClicked)
        }
    }

    val hideFilterSheet = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.CloseFilterClicked)
        }
    }

    val submitFilterSheet: (list: List<BaseCheckboxItemUiModel>?) -> Unit = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.SubmitFilterClicked(it))
        }
    }

    val onSpecialityItemClicked: (Int) -> Unit = remember {
        {
            viewModel.setEvent(BranchDetailsEvent.SelectSpecialty(it))
        }
    }


    BackHandler {
        viewModel.setEvent(BranchDetailsEvent.BackClicked)
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()

    //region Status Bar
    val systemUiController = rememberSystemUiController()
    val onDisposeDarkIcons = !isSystemInDarkTheme()

    val statusBarColor = remember(isSystemInDarkTheme()) {
        if (onDisposeDarkIcons) {
            Color.Transparent
        } else
            Color.White
    }

    val useDarkIcons = remember(toolbarProgress) {
        toolbarProgress <= 0.4F
    }

    LaunchedEffect(key1 = showNetworkError.value){
        if(showNetworkError.value)
            systemUiController.setStatusBarColor(Color.White.copy(alpha = 1 - toolbarProgress),
                darkIcons = true)
    }
    

    DisposableEffect(systemUiController) {

        onDispose {
//            systemUiController.setStatusBarColor(
//                color = statusBarColor,
//                darkIcons = if (!isBackToHome.value) onDisposeDarkIcons else false
//            )
        }
    }


    SideEffect {
        systemUiController.setStatusBarColor(Color.White.copy(alpha = 1 - toolbarProgress),
            darkIcons = if(showNetworkError.value) true else useDarkIcons)
    }

    //endregion

    BottomSheetContainerLayout(
        sheetState= sheetState,
        mainScreenContent = {
            BranchDetailsScreenContent(
                { viewModel.source },
                { branch.value ?: BranchDetailsUIModel() },
                { specialities ?: listOf() },
                { selectedSpecialityId },
                { isRefreshing.value },
                { showNetworkError.value },
                { isSwipeEnabled.value },
                { showFilterIcon },
                { hasFilteredData},
                { state },
                onRefreshAllScreen,
                onRefreshServices,
                onFilterClicked,
                onAllInfoClicked,
                onAllReviewsClicked,
                onNotificationsListClicked,
                onCartClicked,
                onBackClicked,
                onSpecialityItemClicked,
                notificationsCount = viewModel.notificationCount,
                cartCount = viewModel.cartCount
            )
        },bottomSheetContent = {
            OfferedLocationBottomSheet(updateFilterData =updateFilterData ,closeFilter = hideFilterSheet, submitFilter = submitFilterSheet)
        }
    )

    GeneralObservers<BranchDetailsState, BranchDetailsViewModel>(viewModel = viewModel) {
        when (it) {
            is BranchDetailsState.OpenAllInfo -> {
               navigator?.navigate(BranchViewAllScreenDestination(branchId = it.branchId,))
            }
            is BranchDetailsState.OpenAllReviews -> {
                navigator?.navigate(BranchReviewsScreenDestination(it.branchId))

            }
            BranchDetailsState.OpenFilter -> {
                coroutineScope.launch {
                    if (!sheetState.isVisible) sheetState.show()
                }
            }
            BranchDetailsState.FinishScreen -> {
                navigator?.popBackStack()
            }
            BranchDetailsState.OpenCart -> {
                if (viewModel.userModeHandler.isGuestBlocking().not()) {
                    navigator?.navigate(CartScreenDestination)
                }
            }
            BranchDetailsState.OpenNotificationsList -> {
                if (viewModel.userModeHandler.isGuestBlocking().not()) {
                    navigator?.navigate(NotificationsListScreenDestination)
                }

            }
            BranchDetailsState.HideFilter -> {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                }
            }
        }

    }

    HandleLoadingStateObserver(viewModel) {}

}
