package com.trianglz.mimar.modules.authentication.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


sealed class  AuthenticationMode {
    @Parcelize object Default: AuthenticationMode(), Parcelable
    @Parcelize object OnBoarding: AuthenticationMode(), Parcelable
    @Parcelize object Login: AuthenticationMode(), Parcelable
    @Parcelize object CompleteProfile: AuthenticationMode(), Parcelable
    @Parcelize object ChangePassword: AuthenticationMode(), Parcelable
    @Parcelize object VerifyUser: AuthenticationMode(), Parcelable
    @Parcelize object Register: AuthenticationMode(), Parcelable
    @Parcelize object PhoneScreen: AuthenticationMode(), Parcelable


}
