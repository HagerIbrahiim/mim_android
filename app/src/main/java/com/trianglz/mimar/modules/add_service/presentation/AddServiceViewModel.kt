package com.trianglz.mimar.modules.add_service.presentation

import androidx.lifecycle.SavedStateHandle
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.mimar.common.presentation.base.GeneralUpdatesViewModel
import com.trianglz.mimar.modules.add_service.presentation.contract.AddServiceEvent
import com.trianglz.mimar.modules.add_service.presentation.contract.AddServiceState
import com.trianglz.mimar.modules.add_service.presentation.contract.AddServiceViewState
import com.trianglz.mimar.modules.destinations.AddServiceScreenDestination
import com.trianglz.mimar.modules.services.domain.usecase.GetServicesUpdates
import com.trianglz.mimar.modules.services.presentation.mapper.toDomain
import com.trianglz.mimar.modules.services.presentation.source.ServicesListSource
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddServiceViewModel @Inject constructor(
    getServicesUpdates: GetServicesUpdates,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    val source: ServicesListSource,
    private val savedStateHandle: SavedStateHandle,
    val userModeHandler: UserModeHandler,
) : GeneralUpdatesViewModel<AddServiceEvent, AddServiceViewState, AddServiceState>(getUserUpdatesUseCase, getServicesUpdates) {

    init {
        startListenForUserUpdates()
        saveDataFromNavArg()
        source.onAddServiceToCartClicked = {
            setEvent(AddServiceEvent.AddServiceToCart(it))
        }
        getServices()
    }

    private fun getServices() {
        source.apply {
            branchId = viewStates?.branchId
            offeredLocation = viewStates?.offeredLocation
            refreshAll()
        }
    }

    private fun saveDataFromNavArg() {
        AddServiceScreenDestination.argsFrom(savedStateHandle).apply {
            viewStates?.let {
                it.branchId = branchId
                it.offeredLocation = offeredLocation
            }
        }
    }

    override fun handleEvents(event: AddServiceEvent) {
        when (event) {
            is AddServiceEvent.AddServiceToCart -> {
                launchCoroutine {
                    toggleUpdatableItem(event.serviceUIModel.toDomain(), false)
                }
            }

            AddServiceEvent.CartClicked -> setState {
                AddServiceState.OpenCartScreen
            }

        }
    }

    override fun createInitialViewState(): AddServiceViewState {
        return AddServiceViewState()
    }

    override fun updateItem(item: BaseUpdatableItem) {
        setState {
            AddServiceState.CloseScreen
        }
    }
}