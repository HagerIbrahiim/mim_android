package com.trianglz.mimar.modules.sign_in.domain.model

sealed class SocialLoginType(val type: String) {
    object Google : SocialLoginType("google")
    object Facebook : SocialLoginType("facebook")

}