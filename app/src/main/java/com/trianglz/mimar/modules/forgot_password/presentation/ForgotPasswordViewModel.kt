package com.trianglz.mimar.modules.forgot_password.presentation


import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.model.AsyncState
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.forgot_password.domain.model.ForgotPasswordDomainModel
import com.trianglz.mimar.modules.forgot_password.domain.usecase.ForgotPasswordUseCase
import com.trianglz.mimar.modules.forgot_password.presentation.contract.ForgotPasswordEvent
import com.trianglz.mimar.modules.forgot_password.presentation.contract.ForgotPasswordState
import com.trianglz.mimar.modules.forgot_password.presentation.contract.ForgotPasswordViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val forgotPasswordUseCase: ForgotPasswordUseCase) :
    BaseMVIViewModel<ForgotPasswordEvent, ForgotPasswordViewState, ForgotPasswordState>() {

    override fun handleEvents(event: ForgotPasswordEvent) {
        when (event) {
            ForgotPasswordEvent.BackButtonClicked -> {
                setState { ForgotPasswordState.FinishScreen }
            }
            ForgotPasswordEvent.SubmitButtonClicked -> {
                forgotPassword()
            }
            ForgotPasswordEvent.CountryCodeClicked -> {
                setState { ForgotPasswordState.OpenCountriesList }
            }
        }
    }

    override fun createInitialViewState(): ForgotPasswordViewState {
        return ForgotPasswordViewState()
    }

    private fun forgotPassword() {
        val phone = viewStates?.phone?.textFieldValue?.value?.text ?: return
        val dialCode = viewStates?.phone?.countryCode?.value?.first ?: ""

        launchCoroutine {
            setLoading()
            forgotPasswordUseCase.execute(ForgotPasswordDomainModel(phone, dialCode, ))
            setDoneLoading()
            _loadingState.postValue(AsyncState.SuccessWithMessage(StringWrapper(R.string.check_phone_password_sent)))
            setState { ForgotPasswordState.FinishScreen }
        }
    }
}