package com.trianglz.mimar.modules.employee_details.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.presentation.extensions.getActivity
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationActivity
import com.trianglz.mimar.modules.branch_view_all.presentation.contract.BranchViewAllEvent
import com.trianglz.mimar.modules.employee_details.presentation.composables.EmployeeDetailsScreenContent
import com.trianglz.mimar.modules.employee_details.presentation.contract.EmployeeDetailsEvent
import com.trianglz.mimar.modules.employee_details.presentation.contract.EmployeeDetailsState
import com.trianglz.mimar.modules.employee_details.presentation.contract.EmployeeDetailsViewState
import com.trianglz.mimar.modules.employee_details.presentation.model.EmployeeDetailsNavArgs
import com.trianglz.mimar.modules.employee_details.presentation.model.EmployeeDetailsUIModel

@MainGraph
@Composable
@Preview
@Destination(navArgsDelegate = EmployeeDetailsNavArgs::class)
fun EmployeeDetailsScreen(
    viewModel: EmployeeDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null
) {

    //region Status Bar
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
        )

        onDispose {}
    }
    //endregion


    val viewStates = remember {
        viewModel.viewStates ?: EmployeeDetailsViewState()
    }
    val employee = remember {
        viewStates.employee
    }

    val branchName = remember {
        viewStates.branchName
    }
    val isWorkingHoursExpanded = remember {
        viewStates.isWorkingHoursExpanded
    }

    val isOfferedLocationExpanded = remember {
        viewStates.isOfferedLocationExpanded
    }

    val isServicesExpanded = remember {
        viewStates.isServicesExpanded
    }

    val showNetworkError = viewStates.networkError.collectAsState()
    val isRefreshing = viewStates.isRefreshing.collectAsState()
    val onSwipedToRefreshTriggered = remember {
        {
            viewModel.setEvent(EmployeeDetailsEvent.RefreshScreen)
        }
    }

    EmployeeDetailsScreenContent(
        navigator,
        { employee.value ?: EmployeeDetailsUIModel() },
        { showNetworkError.value },
        { isRefreshing.value },
        { isWorkingHoursExpanded },
        { isOfferedLocationExpanded},
        { isServicesExpanded},
        onSwipedToRefreshTriggered,
        branchName ?: ""
    )

    GeneralObservers<EmployeeDetailsState, EmployeeDetailsViewModel>(viewModel = viewModel) {
        when (it) {


            else -> {}
        }

    }

}

