package com.trianglz.mimar.modules.sign_in.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.core.presentation.sociallogin.model.SocialResponseModel
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.sign_in.domain.model.SocialLoginType
import kotlinx.coroutines.flow.MutableStateFlow

sealed class SignInEvent : BaseEvent {
    object SignInClicked : SignInEvent()
    object SkipClicked : SignInEvent()
    object ForgotPasswordClicked : SignInEvent()
    object RegisterNowClicked : SignInEvent()
    object FacebookClicked : SignInEvent()
    object GoogleClicked : SignInEvent()
    object CountryCodeClicked: SignInEvent()
    object ChangeLanguageClicked: SignInEvent()
    data class SocialLogin(
        val socialResponseModel: SocialResponseModel,
        val socialLoginType: SocialLoginType
    ) : SignInEvent()

}

sealed class SignInState : BaseState {
    object OpenHome : SignInState()
    object OpenForgotPassword : SignInState()
    object OpenSignUp : SignInState()
    object ContinueFacebookSignIn : SignInState()
    object ContinueGoogleSignIn : SignInState()
    object OpenCountriesList: SignInState()
    object OpenChangePassword: SignInState()
    object OpenVerifyScreen: SignInState()
    object OpenPhoneScreen: SignInState()
    object OpenCompleteProfile: SignInState()
    object ShowLanguageDialog: SignInState()

}

data class SignInViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val phone: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),

    ) : BaseViewState