package com.trianglz.mimar.modules.splash.presentation.model

import com.trianglz.mimar.modules.authentication.presentation.AuthenticationMode

sealed class SplashResult {
    data class AuthenticationResult(val mode: AuthenticationMode): SplashResult()
    object HomeResult: SplashResult()
    object Error: SplashResult()
}