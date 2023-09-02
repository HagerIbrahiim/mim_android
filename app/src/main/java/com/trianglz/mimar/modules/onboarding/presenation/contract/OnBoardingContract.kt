package com.trianglz.mimar.modules.onboarding.presenation.contract


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import kotlinx.coroutines.flow.MutableStateFlow


/// Events that user performed
sealed class OnBoardingEvent : BaseEvent {
    object OnBoardingViewed : OnBoardingEvent()
    object ScrollToNextPage : OnBoardingEvent()
    object SkipButtonClicked : OnBoardingEvent()
    object ChangeLanguageClicked: OnBoardingEvent()


}

sealed class OnBoardingState : BaseState {
    object OpenLogin : OnBoardingState()
    object ScrollToNextPage : OnBoardingState()
    object OpenHome : OnBoardingState()

    object ShowLanguageDialog: OnBoardingState()

}

data class OnBoardingViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val showSkipButton: MutableState<Boolean> = mutableStateOf(true),
) : BaseViewState