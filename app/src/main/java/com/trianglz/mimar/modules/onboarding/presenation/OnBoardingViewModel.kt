package com.trianglz.mimar.modules.onboarding.presenation

import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.mimar.modules.onboarding.presenation.contract.OnBoardingEvent
import com.trianglz.mimar.modules.onboarding.presenation.contract.OnBoardingState
import com.trianglz.mimar.modules.onboarding.presenation.contract.OnBoardingViewState
import com.trianglz.mimar.modules.user.domain.usecase.SetIsOnBoardingViewedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val setIsOnBoardingViewedUseCase: SetIsOnBoardingViewedUseCase,
) : BaseMVIViewModel<OnBoardingEvent, OnBoardingViewState, OnBoardingState>() {

    override fun handleEvents(event: OnBoardingEvent) {
        when (event) {
            OnBoardingEvent.OnBoardingViewed -> {
                setOnBoardingViewed()
            }
            OnBoardingEvent.ScrollToNextPage -> {
                setState { OnBoardingState.ScrollToNextPage }
            }
            OnBoardingEvent.SkipButtonClicked -> {
                setOnBoardingViewed ()
            }
            OnBoardingEvent.ChangeLanguageClicked -> {
                setState { OnBoardingState.ShowLanguageDialog }
            }
        }
    }

    private fun setOnBoardingViewed() {
        launchCoroutine {
            setIsOnBoardingViewedUseCase.execute(true)
            setState { OnBoardingState.OpenLogin }
        }
    }

    override fun createInitialViewState(): OnBoardingViewState {
        return OnBoardingViewState()
    }

}
