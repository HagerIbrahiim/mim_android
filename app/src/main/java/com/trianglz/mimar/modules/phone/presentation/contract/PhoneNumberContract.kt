package com.trianglz.mimar.modules.phone.presentation.contract

import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.verification.presentation.contract.VerificationEvent
import com.trianglz.mimar.modules.verification.presentation.contract.VerificationState
import kotlinx.coroutines.flow.MutableStateFlow

sealed class PhoneNumberEvent : BaseEvent {
    object SubmitButtonClicked : PhoneNumberEvent()
    object BackButtonClicked : PhoneNumberEvent()
    object CountryCodeClicked: PhoneNumberEvent()
    object LogoutUser: PhoneNumberEvent()


}

sealed class PhoneNumberState : BaseState {
    object FinishScreen : PhoneNumberState()
    object OpenCountriesList: PhoneNumberState()
    object OpenVerifyScreen: PhoneNumberState()
    object OpenLogin: PhoneNumberState()
    object ShowLogoutDialog : PhoneNumberState()


}

data class PhoneNumberViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val phone: TextFieldState = TextFieldState(),
) : BaseViewState