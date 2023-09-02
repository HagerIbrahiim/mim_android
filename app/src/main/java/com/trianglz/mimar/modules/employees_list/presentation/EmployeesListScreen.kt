package com.trianglz.mimar.modules.employees_list.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.core_compose.presentation.helper.LocalNavController
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.employees_list.presentation.composables.EmployeesListScreenContent
import com.trianglz.mimar.modules.employees_list.presentation.contract.EmployeesListEvent
import com.trianglz.mimar.modules.employees_list.presentation.contract.EmployeesListState
import com.trianglz.mimar.modules.employees_list.presentation.contract.EmployeesListViewState
import com.trianglz.mimar.modules.employees_list.presentation.model.EmployeesListNavArgs

@MainGraph
@Destination(navArgsDelegate = EmployeesListNavArgs::class  )
@Composable
fun EmployeesListScreen(viewModel:EmployeesListViewModel = hiltViewModel(),
                        navigator: DestinationsNavigator? = null,
                        resultNavigator: ResultBackNavigator<CartDomainModel>,
) {

    val viewStates = remember {
        viewModel.viewStates ?: EmployeesListViewState()
    }

    val employees = remember {
        viewStates.employeesList
    }

    val showNetworkError = viewStates.networkError.collectAsState()
    val isRefreshing = viewStates.isRefreshing.collectAsState()

    val navHostController = LocalNavController.current
    val showSaveBtn = remember{
        derivedStateOf {
            employees?.isNotEmpty() == true && employees.none { it.showShimmer } && !showNetworkError.value
        }
    }

    val onSwipedToRefreshTriggered = remember {
        {
            viewModel.setEvent(EmployeesListEvent.RefreshScreen)
        }
    }


    val onSaveClicked: () -> Unit = remember {
        {
            viewModel.setEvent(EmployeesListEvent.SaveSelectedEmployeeClicked)
        }
    }

    EmployeesListScreenContent(
        navigator = navigator,
        navHostController = navHostController,
        userModeHandler = viewModel.userModeHandler,
        { employees ?: listOf() },
        {showSaveBtn.value},
        { showNetworkError.value },
        { isRefreshing.value },
        onSwipedToRefreshTriggered,
        onSaveClicked,
        notificationsCount = viewModel.notificationCount,
        cartCount = viewModel.cartCount,

    )

    GeneralObservers<EmployeesListState, EmployeesListViewModel>(viewModel = viewModel) {
        when (it) {
            is EmployeesListState.SendDataToPreviousScreen -> {
                resultNavigator.navigateBack(it.cartDomainModel)
            }
        }

    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }
}