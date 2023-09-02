package com.trianglz.mimar.modules.forgot_password.presentation.contract

import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInEvent
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class ForgotPasswordEvent : BaseEvent {
    object SubmitButtonClicked : ForgotPasswordEvent()
    object BackButtonClicked : ForgotPasswordEvent()
    object CountryCodeClicked: ForgotPasswordEvent()

}

sealed class ForgotPasswordState : BaseState {
    object FinishScreen : ForgotPasswordState()
    object OpenCountriesList: ForgotPasswordState()

}

data class ForgotPasswordViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val phone: TextFieldState = TextFieldState(),
) : BaseViewState