package com.trianglz.mimar.modules.verification.presentation

import android.os.CountDownTimer
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUseCase
import com.trianglz.mimar.modules.user.domain.usecase.LogOutUserUseCase
import com.trianglz.mimar.modules.user.domain.usecase.SetUserUseCase
import com.trianglz.mimar.modules.verification.domain.model.OTPDomainModel
import com.trianglz.mimar.modules.verification.domain.usecase.ResendActivationCodeUseCase
import com.trianglz.mimar.modules.verification.domain.usecase.VerifyAccountUseCase
import com.trianglz.mimar.modules.verification.presentation.contract.VerificationEvent
import com.trianglz.mimar.modules.verification.presentation.contract.VerificationState
import com.trianglz.mimar.modules.verification.presentation.contract.VerificationViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val verifyAccountUseCase: VerifyAccountUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val setUserUseCase: SetUserUseCase,
    private val resendActivationCodeUseCase: ResendActivationCodeUseCase,
    private val logOutUserUseCase: LogOutUserUseCase,
    ) :
    BaseMVIViewModel<VerificationEvent, VerificationViewState, VerificationState>() {

    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        const val COUNTDOWN_TIME = 90000L
    }

    private val _timer: CountDownTimer

    init {
        saveUserPhone()
        _timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                val currentTime = millisUntilFinished / ONE_SECOND
                viewStates?.currentTime?.value = currentTime
                viewStates?.timeInMinutes?.value = currentTime / (60)
                viewStates?.timeInSeconds?.value = ((currentTime) % 60).toInt()
            }

            override fun onFinish() {
                viewStates?.currentTime?.value = DONE
                viewStates?.isResendEnabled?.value = true
            }
        }
        _timer.start()

    }

    override fun createInitialViewState(): VerificationViewState {
        return VerificationViewState()
    }

    override fun handleEvents(event: VerificationEvent) {
        when (event) {
            VerificationEvent.EnableCounterClick -> {
                viewStates?.isResendEnabled?.value = true
            }
            VerificationEvent.SkipClicked -> {
                setState { VerificationState.OpenHome }
            }
            VerificationEvent.SubmitClicked -> {
                verifyPhoneNumber()
            }

            VerificationEvent.TimerClicked -> {
                resendVerificationCode()
            }
            VerificationEvent.BackPresses -> {
                setState { VerificationState.ShowLogoutDialog }
            }
            VerificationEvent.LogoutUser -> {
                logoutUser()
            }
        }
    }

    private fun saveUserPhone(){
        launchCoroutine {
            val user = getUserUseCase.execute()
            val phone = "${user.phoneDialCode}${user.phoneNumber?.trimStart('0')}"
            viewStates?.phoneNumber?.value = phone
        }
    }
    private fun resendVerificationCode() {
        launchCoroutine {
            setLoading()
            viewStates?.isResendEnabled?.value = false
            resendActivationCodeUseCase.execute()
            _timer.start()
            setDoneLoading()
        }
    }

    private fun verifyPhoneNumber() {
        val otp = viewStates?.otp?.value ?: return
        launchCoroutine {
            setLoading()
            val user = verifyAccountUseCase.execute(otpDomainModel = OTPDomainModel(otp))
            setUserUseCase.execute(user)
            setDoneLoading()
            setState { viewStates?.phoneNumber?.value?.let { VerificationState.OpenSetupProfile(it) } }
        }
    }

    private fun logoutUser(){
        launchCoroutine {
            setLoading()
            logOutUserUseCase.execute()
            setDoneLoading()
            setState { VerificationState.OpenLogin }
        }
    }
}