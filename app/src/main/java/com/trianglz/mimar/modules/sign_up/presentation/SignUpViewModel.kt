/*
 * *
 *  * Created by Ahmed Awad on 1/3/23, 12:14 PM
 *
 */

package com.trianglz.mimar.modules.sign_up.presentation

import com.trianglz.core.di.qualifiers.DeviceId
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.helper.getFcmTokenOrNull
import com.trianglz.mimar.modules.sign_up.domain.model.SignUpDomainModel
import com.trianglz.mimar.modules.sign_up.domain.usecase.SignUpUseCase
import com.trianglz.mimar.modules.sign_up.presentation.contract.SignUpEvent
import com.trianglz.mimar.modules.sign_up.presentation.contract.SignUpState
import com.trianglz.mimar.modules.sign_up.presentation.contract.SignUpViewState
import com.trianglz.mimar.modules.user.domain.usecase.SetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    @DeviceId private val deviceId: String,
    private val setUserUseCase: SetUserUseCase,
    ) :
    BaseMVIViewModel<SignUpEvent, SignUpViewState, SignUpState>() {


    override fun handleEvents(event: SignUpEvent) {
        when (event) {
            SignUpEvent.NextClicked -> {
                signUp()
            }
            SignUpEvent.TermsAndConditionsClicked -> {
                setState { SignUpState.OpenTermsAndConditions }
            }
            SignUpEvent.BackPresses -> {
                setState { SignUpState.BackPressed }
            }
            SignUpEvent.CountryCodeClicked -> {
                setState { SignUpState.OpenCountriesList }
            }

            SignUpEvent.SkipButtonClicked -> {
                setState { SignUpState.OpenHome }
            }
            SignUpEvent.SignInClicked -> {
                setState { SignUpState.BackPressed }
            }
        }
    }

    override fun createInitialViewState(): SignUpViewState {
        return SignUpViewState()
    }

    private fun signUp() {

        launchCoroutine {
            
            setLoading()
            val fcmToken = getFcmTokenOrNull()
            val phone = viewStates?.phone?.textFieldValue?.value?.text ?: ""
            val password = viewStates?.password?.textFieldValue?.value?.text ?: ""

            val dialCode = viewStates?.phone?.countryCode?.value?.first ?: ""
            val signUpDomainModel = SignUpDomainModel(
                phone, dialCode, password, deviceId, fcmToken,
            )

            val user = signUpUseCase.execute(signUpDomainModel)
            setUserUseCase.execute(user, true)
            setDoneLoading()
            setState { SignUpState.OpenVerification }
        }

    }
}