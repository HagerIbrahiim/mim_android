package com.trianglz.mimar.modules.employee_details.presentation.contract


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.branch_details.presentation.contract.BranchDetailsEvent
import com.trianglz.mimar.modules.employee_details.presentation.model.EmployeeDetailsUIModel
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import kotlinx.coroutines.flow.MutableStateFlow


/// Events that user performed
sealed class EmployeeDetailsEvent : BaseEvent {
    object RefreshScreen: EmployeeDetailsEvent()
    data class AddServiceToCart(val service: ServiceUIModel) : EmployeeDetailsEvent()

}

sealed class EmployeeDetailsState : BaseState {


}

data class EmployeeDetailsViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val employee: MutableState<EmployeeDetailsUIModel?> = mutableStateOf(null),
    val isServicesExpanded: MutableState<Boolean> = mutableStateOf(false),
    val isOfferedLocationExpanded: MutableState<Boolean> = mutableStateOf(false),
    val isWorkingHoursExpanded: MutableState<Boolean> = mutableStateOf(false),
    var employeeId: Int? = null,
    var branchName: String?= "",
) : BaseViewState