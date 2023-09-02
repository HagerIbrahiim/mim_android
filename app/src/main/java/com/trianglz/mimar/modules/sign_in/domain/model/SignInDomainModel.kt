package com.trianglz.mimar.modules.sign_in.domain.model


data class SignInDomainModel(
    val phoneNumber: String,
    val password: String,
    val deviceId: String,
    val fcmToken: String,
    val dialCode: String,
    )
