package com.trianglz.mimar.modules.sign_in.presentation

import androidx.lifecycle.viewModelScope
import com.trianglz.core.di.qualifiers.DeviceId
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.helper.ExceptionHandler
import com.trianglz.core.presentation.helper.getFcmTokenOrNull
import com.trianglz.core.presentation.sociallogin.model.SocialResponseModel
import com.trianglz.mimar.modules.sign_in.domain.model.SignInDomainModel
import com.trianglz.mimar.modules.sign_in.domain.model.SocialLoginDomainModel
import com.trianglz.mimar.modules.sign_in.domain.model.SocialLoginType
import com.trianglz.mimar.modules.sign_in.domain.usecase.SignInUseCase
import com.trianglz.mimar.modules.sign_in.domain.usecase.SocialLoginUseCase
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInEvent
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInState
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInState.*
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInViewState
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.user.domain.usecase.SetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val setUserUseCase: SetUserUseCase,
    private val signInUseCase: SignInUseCase,
    @DeviceId private val deviceId: String,
    private val socialLoginUseCase: SocialLoginUseCase,

    ) : BaseMVIViewModel<SignInEvent, SignInViewState, SignInState>() {


    private val loginExceptionHandler = exceptionHandler {
        _loadingState.postValue(ExceptionHandler.parse(it, true))
    }

    override fun handleEvents(event: SignInEvent) {
        when (event) {
            SignInEvent.SignInClicked -> {
                login()
            }
            SignInEvent.ForgotPasswordClicked -> {
                setState { OpenForgotPassword }
            }
            SignInEvent.RegisterNowClicked -> {
                setState { OpenSignUp }
            }
            SignInEvent.SkipClicked -> {
                setState { OpenHome }
            }
            SignInEvent.FacebookClicked -> {
                setState { ContinueFacebookSignIn }
            }
            SignInEvent.GoogleClicked -> {
                setState { ContinueGoogleSignIn }
            }
            SignInEvent.CountryCodeClicked -> {
                setState { OpenCountriesList }

            }
            SignInEvent.ChangeLanguageClicked -> {
                setState { ShowLanguageDialog }
            }
            is SignInEvent.SocialLogin -> {
                socialLogin(event.socialResponseModel, event.socialLoginType)
            }
        }
    }

    override fun createInitialViewState(): SignInViewState {
        return SignInViewState()
    }

    private fun login() {
        val phone = viewStates?.phone?.textFieldValue?.value?.text ?: return
        val password = viewStates?.password?.textFieldValue?.value?.text ?: return
        val dialCode = viewStates?.phone?.countryCode?.value?.first ?: ""

        viewModelScope.launch(loginExceptionHandler + SupervisorJob()) {
            val fcm = getFcmTokenOrNull()
            setLoading()
            val user = signInUseCase.execute(
                signInDomainModel = SignInDomainModel(
                    phoneNumber = phone,
                    password = password,
                    fcmToken = fcm ?: "",
                    dialCode = dialCode,
                    deviceId = deviceId,
                )
            )
            setUserUseCase.execute(user)
            setDoneLoading()
            setupNavigation(user)
        }

    }

    private fun socialLogin(
        socialResponseModel: SocialResponseModel,
        socialLoginType: SocialLoginType
    ) {
        viewModelScope.launch(loginExceptionHandler + SupervisorJob()) {
            setLoading()
            val fcmToken = getFcmTokenOrNull()
            val socialLoginDomainModel = SocialLoginDomainModel(
                accessToken = socialResponseModel.idToken,
                name = socialResponseModel.userName,
                deviceId = deviceId,
                fcmToken = fcmToken,
                socialProviderId = socialResponseModel.id,
                socialProviderType = socialLoginType.type,
            )
            val user = socialLoginUseCase.execute(socialLoginDomainModel)
            setUserUseCase.execute(user)
            setDoneLoading()
            setupNavigation(user)
        }
    }
    private fun setupNavigation(user: UserDomainModel) {
        if(user.phoneNumber == null)
            setState { OpenPhoneScreen }
        else if (user.isPhoneVerified == false)
            setState { OpenVerifyScreen }
        else if (user.mustChangePassword == true)
            setState { OpenChangePassword }
        else if (user.isPhoneVerified == true && user.isProfileCompleted == false)
            setState { OpenCompleteProfile }
        else if (user.isProfileCompleted == true)
            setState { OpenHome }
    }


}
