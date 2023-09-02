package com.trianglz.mimar.modules.home.presentation.contracts

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import kotlinx.coroutines.flow.MutableStateFlow

// Events that user performed
sealed class HomeActivityEvent : BaseEvent {
    object UpdateUser : HomeActivityEvent()
    object UpdateNotificationCount : HomeActivityEvent()
    data class ClearCartAndAddNewService(val serviceId: Int) : HomeActivityEvent()
    data class EmitRequestPaymentStatus(val checkoutId: String) : HomeActivityEvent()
    object CartClicked : HomeActivityEvent()
    data class  AppointmentClickedEvent(val id: Int) : HomeActivityEvent()
    data class  SubmitAppointmentReviewClicked(val id: Int) : HomeActivityEvent()
    data class ShowDeleteAccountErrorMsg(val showMessage: Boolean?): HomeActivityEvent()
}

sealed class HomeActivityState : BaseState {
    object Idle : HomeActivityState()
    object OpenLogin : HomeActivityState()
    object ShowGuestDialog : HomeActivityState()
    data class ShowAddToCartValidationDialog(val newServiceId: Int, val branchName: String) : HomeActivityState()
    object OpenCart : HomeActivityState()
    data class  OpenAppointment(val id: Int) : HomeActivityState()
    data class  OpenSubmitAppointmentReview(val id: Int) : HomeActivityState()
}

data class HomeViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var user: MutableState<UserUIModel?> = mutableStateOf(null),
    ) : BaseViewState