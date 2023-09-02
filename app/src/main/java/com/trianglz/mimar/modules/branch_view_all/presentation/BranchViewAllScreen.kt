package com.trianglz.mimar.modules.branch_view_all.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.extensions.openGoogleMaps
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.branch_view_all.presentation.composables.BranchViewAllScreenContent
import com.trianglz.mimar.modules.branch_view_all.presentation.contract.BranchViewAllEvent
import com.trianglz.mimar.modules.branch_view_all.presentation.contract.BranchViewAllState
import com.trianglz.mimar.modules.branch_view_all.presentation.contract.BranchViewAllViewState
import com.trianglz.mimar.modules.branch_view_all.presentation.model.BranchViewAllNavArgs
import com.trianglz.mimar.modules.destinations.BranchDetailsScreenDestination
import com.trianglz.mimar.modules.destinations.EmployeeDetailsScreenDestination
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType

@Composable
@Destination(navArgsDelegate = BranchViewAllNavArgs::class)
@MainGraph
fun BranchViewAllScreen(
    navigator: DestinationsNavigator,
    viewModel: BranchViewAllViewModel = hiltViewModel(),

    ) {

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

    val context = LocalContext.current

    val viewStates = remember {
        viewModel.viewStates ?: BranchViewAllViewState()
    }

    val branchDetails by remember {
        viewStates.branchDetails
    }

    val onMapClicked = remember {
        {
            viewModel.setEvent(BranchViewAllEvent.OnMapClicked)
        }
    }
    val showNetworkError = viewStates.networkError.collectAsState()
    val isRefreshing = viewStates.isRefreshing.collectAsState()
    val onSwipedToRefreshTriggered = remember {
        {
            viewModel.setEvent(BranchViewAllEvent.RefreshScreen)
        }
    }

    val otherBranches = remember {
        viewStates.otherBranches
    }

    val staffMembers = remember {
        viewStates.branchStaffMembers
    }

    val isLoading = remember {
        viewStates.isLoading
    }

    val isSupportedAreasExpanded = remember {
        viewStates.isSupportedAreasExpanded
    }

    val isStaffMembersExpanded = remember {
        viewStates.isStaffExpanded
    }

    val isOtherBranchesExpanded = remember {
        viewStates.isOtherBranchesExpanded
    }

    val isWorkingHoursExpanded = remember {
        viewStates.isWorkingHoursExpanded
    }


    val locationText = remember(branchDetails) {
        val branchOfferedLocationName = branchDetails?.offeredLocation?.name
        if (!branchOfferedLocationName?.getString(context).isNullOrEmpty())
            branchOfferedLocationName else
            StringWrapper(R.string.no_provided_location)
    }


    val showMap = remember(branchDetails) {
        branchDetails?.offeredLocation != OfferedLocationType.Home
    }


    BranchViewAllScreenContent(
        navigator = navigator,
        userModeHandler = viewModel.userModeHandler,
        showMap = { showMap },
        branchName = { branchDetails?.name.orEmpty() },
        lat = { branchDetails?.lat ?: 0.0 },
        lng = { branchDetails?.lng ?: 0.0 },
        locationText = { locationText ?: StringWrapper() },
        workingHours = { branchDetails?.workingHours.orEmpty() },
        staff = { staffMembers },
        areas = { branchDetails?.coveredZones.orEmpty() },
        otherBranches = { otherBranches },
        isOtherBranchesExpanded = { isOtherBranchesExpanded },
        isStaffExpanded = { isStaffMembersExpanded },
        isWorkingHoursExpanded = { isWorkingHoursExpanded },
        isSupportedAreasExpanded = { isSupportedAreasExpanded },
        onMapClicked = onMapClicked,
        showNetworkError = { showNetworkError.value },
        isRefreshing = { isRefreshing.value },
        isLoading = { isLoading.value ?: false },
        onSwipeToRefresh = onSwipedToRefreshTriggered,
        notificationsCount = viewModel.notificationCount,
        cartCount = viewModel.cartCount
    )

    GeneralObservers<BranchViewAllState, BranchViewAllViewModel>(viewModel = viewModel) {
        when (it) {

            is BranchViewAllState.OpenMap -> {
                it.branch?.let { branch ->
                    context.openGoogleMaps(branch.name, branch.lat ?: 0.0, branch.lng ?: 0.0)
                }

            }
            is BranchViewAllState.OpenBranchScreen -> {
                navigator.navigate(BranchDetailsScreenDestination(branchId = it.branchId))
            }
            is BranchViewAllState.OpenEmployeeDetailsScreen -> {

                navigator.navigate(EmployeeDetailsScreenDestination(
                    employeeId = it.employeeId , branchName = it.branchName))
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }
}

