package com.trianglz.mimar.modules.branch_view_all.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.mimar.common.presentation.base.GeneralUpdatesViewModel
import com.trianglz.mimar.modules.branch_view_all.presentation.contract.BranchViewAllEvent
import com.trianglz.mimar.modules.branch_view_all.presentation.contract.BranchViewAllState
import com.trianglz.mimar.modules.branch_view_all.presentation.contract.BranchViewAllViewState
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchDetailsUseCase
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchFavouritesUpdates
import com.trianglz.mimar.modules.branches.domain.usecase.GetOtherBranchServiceProviderBranches
import com.trianglz.mimar.modules.branches.presentation.mapper.toDomainModel
import com.trianglz.mimar.common.presentation.mapper.toUI
import com.trianglz.mimar.modules.branches.presentation.mapper.toUI
import com.trianglz.mimar.modules.branches.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.destinations.BranchViewAllScreenDestination
import com.trianglz.mimar.modules.employee.domain.usecase.GetAllEmployeesUseCase
import com.trianglz.mimar.modules.employee.presentation.mapper.toUI
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BranchViewAllViewModel @Inject constructor(
    private val getBranchDetailsUseCase: GetBranchDetailsUseCase,
    private val getOtherBranchServiceProviderBranches: GetOtherBranchServiceProviderBranches,
    getBranchFavouritesUpdates: GetBranchFavouritesUpdates,
    private val savedStateHandle: SavedStateHandle,
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase,
    val userModeHandler: UserModeHandler,
    getUserUpdatesUseCase: GetUserUpdatesUseCase
) : GeneralUpdatesViewModel<BranchViewAllEvent, BranchViewAllViewState, BranchViewAllState>(getUserUpdatesUseCase, getBranchFavouritesUpdates) {


    private val branchDataExceptionHandler = exceptionHandler {
        viewStates?.isRefreshing?.value = false
        viewStates?.networkError?.value = true
        viewStates?.isLoading?.value = false
    }
    init {
        startListenForUserUpdates()
        saveBranchIdFromNavArg()
    }

    private fun saveBranchIdFromNavArg() {
        val args = BranchViewAllScreenDestination.argsFrom(savedStateHandle)
        viewStates?.branchId?.value = args.branchId
        getData()

    }

    private fun getData() {
        resetViewStates()
        viewModelScope.launch(branchDataExceptionHandler) {
            viewStates?.isLoading?.value = true
            val requests =
                setOf(
                    async { getBranchDetails() },
                    async { getOtherBranches() },
                    async { getBranchStaffMembers() },
                )
            requests.forEach {
                it.await()
            }
            viewStates?.isLoading?.value = false
            setDoneLoading()
        }
    }

    private suspend fun getBranchDetails() {
        viewStates?.branchDetails?.value = getBranchDetailsUseCase.execute(viewStates?.branchId?.value ?:-1).toUI()
        viewStates?.isRefreshing?.value = false
    }

    private suspend fun getOtherBranches(){
        viewStates?.otherBranches?.clear()
        viewStates?.otherBranches?.addAll(BranchUIModel.getShimmerList())
        val otherBranches = getOtherBranchServiceProviderBranches.execute(viewStates?.branchId?.value ?:-1).map { it.toUIModel(
            onClick = {
                setEvent(BranchViewAllEvent.OtherBranchClicked(it))
            }, onFavoriteClick = {
                setEvent(BranchViewAllEvent.ToggleBranchFavoriteClicked(it))
            },
        ) }

        viewStates?.otherBranches?.clear()
        viewStates?.otherBranches?.addAll(otherBranches)

    }

    private suspend fun getBranchStaffMembers(){
        val staffList = getAllEmployeesUseCase.execute(branchId = viewStates?.branchId?.value ?:-1).map {
            it.toUI {
                setEvent(BranchViewAllEvent.EmployeeItemClicked(it))
            }
        }
        viewStates?.branchStaffMembers?.clear()
        viewStates?.branchStaffMembers?.addAll(staffList)

    }
    override fun handleEvents(event: BranchViewAllEvent) {
        when (event) {

            BranchViewAllEvent.OnMapClicked -> {
                setState { BranchViewAllState.OpenMap(viewStates?.branchDetails?.value) }
            }

            BranchViewAllEvent.RefreshScreen -> {
                viewStates?.isRefreshing?.value = true
                getData()
            }

            is BranchViewAllEvent.ToggleBranchFavoriteClicked -> {
                toggleUpdatableItem(event.item.toDomainModel(), event.item.isFavorite)

            }
            is BranchViewAllEvent.OtherBranchClicked -> {
                setState { BranchViewAllState.OpenBranchScreen(event.branchId) }
            }
            is BranchViewAllEvent.EmployeeItemClicked -> {
                setState {
                    BranchViewAllState.OpenEmployeeDetailsScreen(
                        event.employeeId,
                        viewStates?.branchDetails?.value?.name ?: ""
                    )
                }
            }
        }
    }

    override fun updateItem(item: BaseUpdatableItem) {
        when (item) {
            is BranchDomainModel -> {
                val list = viewStates?.otherBranches
                val currentBranch = list?.find { it.id == item.id }
                val currentIndex = list?.indexOf(currentBranch)

                if (currentIndex != null && currentIndex != -1 ) {
                    list.removeAt(currentIndex)
                    list.add(currentIndex, item.toUIModel(
                        onClick = {
                            setEvent(BranchViewAllEvent.OtherBranchClicked(it))
                        }, onFavoriteClick = {
                            setEvent(BranchViewAllEvent.ToggleBranchFavoriteClicked(it))
                        },
                    ))

                }
            }
        }
    }
    override fun resetViewStates() {
        viewStates?.networkError?.value = false

    }
    override fun createInitialViewState(): BranchViewAllViewState {
        return BranchViewAllViewState()
    }
}