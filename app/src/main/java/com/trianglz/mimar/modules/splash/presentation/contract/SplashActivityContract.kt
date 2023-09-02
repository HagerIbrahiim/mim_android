package com.trianglz.mimar.modules.splash.presentation.contract

import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.mimar.modules.splash.presentation.model.SplashResult

sealed class SplashActivityState: BaseState {
    data class SplashResultState(val result: SplashResult): SplashActivityState()
}