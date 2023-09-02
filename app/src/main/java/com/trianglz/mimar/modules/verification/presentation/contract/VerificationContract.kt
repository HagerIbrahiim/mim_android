package com.trianglz.mimar.modules.verification.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.setup_profile.presentation.contract.SetupProfileEvent
import com.trianglz.mimar.modules.setup_profile.presentation.contract.SetupProfileState
import kotlinx.coroutines.flow.MutableStateFlow

sealed class VerificationEvent : BaseEvent {
    object SubmitClicked : VerificationEvent()
    object SkipClicked : VerificationEvent()
    object EnableCounterClick : VerificationEvent()
    object TimerClicked : VerificationEvent()
    object BackPresses : VerificationEvent()
    object LogoutUser: VerificationEvent()

}

sealed class VerificationState : BaseState {
    object OpenHome : VerificationState()
    data class OpenSetupProfile(val phoneNumber: String) : VerificationState()
    object OpenLogin: VerificationState()
    object ShowLogoutDialog : VerificationState()

}

data class VerificationViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var isResendEnabled: MutableState<Boolean> = mutableStateOf(false),
    var currentTime: MutableState<Long> = mutableStateOf(0L),
    var timeInMinutes: MutableState<Long> = mutableStateOf(0L),
    var timeInSeconds: MutableState<Int> = mutableStateOf(0),
    val otp: MutableState<String> = mutableStateOf(""),
    val phoneNumber: MutableState<String> = mutableStateOf(""),
    val isConsumed: MutableState<Boolean> = mutableStateOf(false),

    ) : BaseViewState