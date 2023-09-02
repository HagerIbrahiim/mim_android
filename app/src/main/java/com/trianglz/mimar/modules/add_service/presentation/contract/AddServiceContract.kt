package com.trianglz.mimar.modules.add_service.presentation.contract

import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import kotlinx.coroutines.flow.MutableStateFlow


sealed class AddServiceEvent : BaseEvent {
    object CartClicked : AddServiceEvent()
    data class AddServiceToCart(val serviceUIModel: ServiceUIModel) : AddServiceEvent()
}

sealed class AddServiceState : BaseState {
    object CloseScreen : AddServiceState()
    object OpenCartScreen : AddServiceState()
}

data class AddServiceViewState(
    var branchId: Int = 0,
    var specialityId: Int = 0,
    var offeredLocation: String? = null,
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
) : BaseViewState