package com.trianglz.mimar.modules.phone.presentation

import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.model.AsyncState
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.phone.presentation.contract.PhoneNumberEvent
import com.trianglz.mimar.modules.phone.presentation.contract.PhoneNumberState
import com.trianglz.mimar.modules.phone.presentation.contract.PhoneNumberViewState
import com.trianglz.mimar.modules.user.domain.model.UpdateUserDomainModel
import com.trianglz.mimar.modules.user.domain.usecase.LogOutUserUseCase
import com.trianglz.mimar.modules.user.domain.usecase.UpdateUserUseCase
import com.trianglz.mimar.modules.verification.presentation.contract.VerificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhoneNumberViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val logOutUserUseCase: LogOutUserUseCase,

    ) : BaseMVIViewModel<PhoneNumberEvent, PhoneNumberViewState, PhoneNumberState>() {

    override fun handleEvents(event: PhoneNumberEvent) {
        when (event) {
            PhoneNumberEvent.BackButtonClicked -> {
                setState { PhoneNumberState.ShowLogoutDialog }
            }
            PhoneNumberEvent.SubmitButtonClicked -> {
                updateUserWithPhone()
            }
            PhoneNumberEvent.CountryCodeClicked -> {
                setState { PhoneNumberState.OpenCountriesList }
            }
            PhoneNumberEvent.LogoutUser -> {
                logoutUser()
            }
        }
    }

    override fun createInitialViewState(): PhoneNumberViewState {
        return PhoneNumberViewState()
    }

    private fun updateUserWithPhone() {
        val phone = viewStates?.phone?.textFieldValue?.value?.text ?: return
        val dialCode = viewStates?.phone?.countryCode?.value?.first ?: ""

        launchCoroutine {
            setLoading()
            updateUserUseCase.execute(UpdateUserDomainModel(phoneNumber = phone, dialCode = dialCode))
            setDoneLoading()
            setState { PhoneNumberState.OpenVerifyScreen }
        }
    }

    private fun logoutUser(){
        launchCoroutine {
            setLoading()
            logOutUserUseCase.execute()
            setDoneLoading()
            setState { PhoneNumberState.OpenLogin }
        }
    }
}