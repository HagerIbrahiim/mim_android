package com.trianglz.mimar.modules.employees_list.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.trianglz.mimar.common.presentation.base.MimarBaseViewModel
import com.trianglz.mimar.modules.cart.domain.usecase.ConnectGetCartUpdatesUseCase
import com.trianglz.mimar.modules.cart.domain.usecase.UpdateBranchServiceInCartUseCase
import com.trianglz.mimar.modules.cart.presentation.mapper.toUI
import com.trianglz.mimar.modules.destinations.EmployeesListScreenDestination
import com.trianglz.mimar.modules.employee.domain.usecase.GetAllEmployeesUseCase
import com.trianglz.mimar.modules.employee.presentation.mapper.toUI
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import com.trianglz.mimar.modules.employees_list.presentation.contract.EmployeesListEvent
import com.trianglz.mimar.modules.employees_list.presentation.contract.EmployeesListState
import com.trianglz.mimar.modules.employees_list.presentation.contract.EmployeesListViewState
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesListViewModel @Inject constructor(
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val updateBranchServiceInCartUseCase: UpdateBranchServiceInCartUseCase,
    val userModeHandler: UserModeHandler,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,

    ) : MimarBaseViewModel<EmployeesListEvent, EmployeesListViewState, EmployeesListState>(getUserUpdatesUseCase) {

    private val employeesExceptionHandler = exceptionHandler {

        viewStates?.isRefreshing?.value = false
        viewStates?.networkError?.value = true

    }


    override fun handleEvents(event: EmployeesListEvent) {
        when (event) {
            is EmployeesListEvent.EmployeeItemClicked -> {
                handleClickedEmployee(event.id)
            }
            EmployeesListEvent.RefreshScreen -> {
                viewStates?.isRefreshing?.value = false
                getEmployeesList()
            }
            EmployeesListEvent.SaveSelectedEmployeeClicked -> {
                viewStates?.selectedEmployeeId?.let {
                    if(it > -1) updateServiceEmployee(it)
                }
            }
        }
    }

    init {
        startListenForUserUpdates()
        saveDataFromNavArg()
        getEmployeesList()
    }

    private fun saveDataFromNavArg() {
        val args = EmployeesListScreenDestination.argsFrom(savedStateHandle)

        viewStates?.selectedEmployeeId = args.selectedEmployeeId
        args.offeredLocation?.let {
            viewStates?.offeredLocation = it
        }
        args.date?.let {
            viewStates?.date = it
        }
        args.cartBranchServiceId?.let {
            viewStates?.cartBranchServiceId = it
        }
    }


    private fun handleClickedEmployee(employeeId: Int){
        if (employeeId != viewStates?.selectedEmployee?.value?.id) {
            viewStates?.selectedEmployeeId = employeeId
            val employeesList = viewStates?.employeesList

            val oldSelectedEmployeeIndex = employeesList?.indexOfFirst { it.isChecked.value }
            val newSelectedEmployeeIndex = employeesList?.indexOfFirst { it.id == employeeId }

            if (oldSelectedEmployeeIndex != null && oldSelectedEmployeeIndex != -1) {
                employeesList.getOrNull(oldSelectedEmployeeIndex)?.isChecked?.value = false
            }
            if (newSelectedEmployeeIndex != null && newSelectedEmployeeIndex != -1) {
                val updatedSpecialty = employeesList[newSelectedEmployeeIndex]
                updatedSpecialty.isChecked.value = true
                employeesList[newSelectedEmployeeIndex] = updatedSpecialty
                viewStates?.selectedEmployee?.value = updatedSpecialty
            }

        }
    }

    private fun getEmployeesList() {
        resetViewStates()
       viewModelScope.launch(employeesExceptionHandler) {
            viewStates?.employeesList?.clear()
            viewStates?.employeesList?.addAll(EmployeeUIModel.getShimmerList())
            val employeesList = getAllEmployeesUseCase.execute(
                cartBranchServiceId = viewStates?.cartBranchServiceId,
                offeredLocation = viewStates?.offeredLocation,
                date = viewStates?.date
            ).map {
                it.toUI(onClick = {
                    setEvent(EmployeesListEvent.EmployeeItemClicked(it))
                })
            }

            if (employeesList.isNotEmpty()) {
                val selectedEmployee = employeesList.find { it.id == viewStates?.selectedEmployeeId }

                selectedEmployee?.isChecked?.value = true
                viewStates?.employeesList?.clear()
                viewStates?.employeesList?.addAll(employeesList)
                viewStates?.selectedEmployee?.value = selectedEmployee
            }
            else {
                viewStates?.employeesList?.clear()
            }
            viewStates?.isRefreshing?.value = false
        }


    }

    private fun updateServiceEmployee(employeeId: Int) {
        viewStates?.cartBranchServiceId?.let {branchServiceId ->
            launchCoroutine {
                setLoading()
                val cartDomain = updateBranchServiceInCartUseCase.execute(branchServiceId = branchServiceId, employeeId = employeeId)
                setDoneLoading()
                cartDomain?.let {
                    setState { EmployeesListState.SendDataToPreviousScreen( cartDomain ) }
                }

            }
        }
    }



    override fun resetViewStates() {
        viewStates?.networkError?.value = false
        viewStates?.employeesList?.clear()
        viewStates?.employeesList?.addAll(EmployeeUIModel.getShimmerList())

    }
    override fun createInitialViewState(): EmployeesListViewState {
        return EmployeesListViewState()
    }

}
