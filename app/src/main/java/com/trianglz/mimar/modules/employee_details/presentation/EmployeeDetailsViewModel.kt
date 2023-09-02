package com.trianglz.mimar.modules.employee_details.presentation

import androidx.lifecycle.SavedStateHandle
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.mimar.common.presentation.base.GeneralUpdatesViewModel
import com.trianglz.mimar.modules.destinations.EmployeeDetailsScreenDestination
import com.trianglz.mimar.modules.employee_details.domain.usecase.GetEmployeeDetailsUseCase
import com.trianglz.mimar.modules.employee_details.presentation.contract.EmployeeDetailsEvent
import com.trianglz.mimar.modules.employee_details.presentation.contract.EmployeeDetailsState
import com.trianglz.mimar.modules.employee_details.presentation.contract.EmployeeDetailsViewState
import com.trianglz.mimar.modules.employee_details.presentation.mapper.toUI
import com.trianglz.mimar.modules.employee_details.presentation.model.EmployeeDetailsUIModel
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.services.domain.usecase.GetServicesUpdates
import com.trianglz.mimar.modules.services.presentation.mapper.toDomain
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailsViewModel @Inject constructor(
    private val getEmployeeDetailsUseCase: GetEmployeeDetailsUseCase,
    private val savedStateHandle: SavedStateHandle,
    getServicesUpdates: GetServicesUpdates,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    ) : GeneralUpdatesViewModel<EmployeeDetailsEvent, EmployeeDetailsViewState, EmployeeDetailsState>(
        getUserUpdatesUseCase, getServicesUpdates
    ) {

    private val employeeExceptionHandler = exceptionHandler {
        viewStates?.isRefreshing?.value = false
        viewStates?.networkError?.value = true
        viewStates?.employee?.value = null
    }

    init {
        getNavArgsData()
    }

    override fun handleEvents(event: EmployeeDetailsEvent) {
        when (event) {
            is EmployeeDetailsEvent.AddServiceToCart -> {
                toggleUpdatableItem(event.service.toDomain(), false)
            }
            EmployeeDetailsEvent.RefreshScreen -> {
                getEmployeeDetails()
            }
        }
    }

    private fun getNavArgsData() {
        EmployeeDetailsScreenDestination.argsFrom(savedStateHandle).apply {
            viewStates?.employeeId = this.employeeId
            viewStates?.branchName = this.branchName
            getEmployeeDetails()
        }
    }

    private fun getEmployeeDetails() {
        viewStates?.employeeId?.let {
            resetViewStates()
            launchCoroutine(employeeExceptionHandler) {
                viewStates?.employee?.value = EmployeeDetailsUIModel.getShimmer()
                val employee = getEmployeeDetailsUseCase.execute(it)
                viewStates?.employee?.value = employee.toUI {
                    setEvent(EmployeeDetailsEvent.AddServiceToCart(it))
                }
            }
        }
    }

    override fun resetViewStates() {
        viewStates?.networkError?.value = false
        viewStates?.employee?.value = EmployeeDetailsUIModel.getShimmer()
    }
    override fun updateItem(item: BaseUpdatableItem) {
        when (item) {
            is ServiceDomainModel -> {
                val list =
                    viewStates?.employee?.value?.services?.flatMap { it.services ?: listOf() }
                val currentSession = list?.find { it.uniqueId == item.uniqueId }
                currentSession?.isAdded?.value = item.isAdded ?: false
            }
        }
    }


    override fun createInitialViewState(): EmployeeDetailsViewState {
        return EmployeeDetailsViewState()
    }

}
