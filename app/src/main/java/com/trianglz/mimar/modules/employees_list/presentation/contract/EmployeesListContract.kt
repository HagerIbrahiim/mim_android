package com.trianglz.mimar.modules.employees_list.presentation.contract


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import kotlinx.coroutines.flow.MutableStateFlow


/// Events that user performed
sealed class EmployeesListEvent : BaseEvent {

    object RefreshScreen: EmployeesListEvent()
    object SaveSelectedEmployeeClicked: EmployeesListEvent()
    data class EmployeeItemClicked(val id: Int): EmployeesListEvent()

}

sealed class EmployeesListState : BaseState {
    data class SendDataToPreviousScreen(val cartDomainModel: CartDomainModel): EmployeesListState()

}

data class EmployeesListViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var employeesList: SnapshotStateList<EmployeeUIModel>? = mutableStateListOf(),
    val selectedEmployee: MutableState<EmployeeUIModel?> = mutableStateOf(null),

    var selectedEmployeeId: Int? = null,
    var cartBranchServiceId: Int? = null,
    var offeredLocation: String? = null,
    var date: String? = null,
    ) : BaseViewState