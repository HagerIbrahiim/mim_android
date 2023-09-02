package com.trianglz.mimar.modules.sign_up.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import kotlinx.coroutines.flow.MutableStateFlow

sealed class SignUpEvent : BaseEvent {
    object TermsAndConditionsClicked : SignUpEvent()
    object NextClicked : SignUpEvent()
    object BackPresses : SignUpEvent()
    object CountryCodeClicked: SignUpEvent()

    object SkipButtonClicked: SignUpEvent()

    object SignInClicked: SignUpEvent()


}

sealed class SignUpState : BaseState {
    object OpenVerification : SignUpState()
    object OpenTermsAndConditions : SignUpState()
    object BackPressed : SignUpState()
    object OpenCountriesList: SignUpState()
    object OpenHome: SignUpState()

}

data class SignUpViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val phone: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val confirmPassword: TextFieldState = TextFieldState(),

    ) : BaseViewState