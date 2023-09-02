package com.trianglz.mimar.modules.sign_up.domain.model

data class SignUpDomainModel(
    val phoneNumber: String,
    val dialCode: String,
    val password: String,
    val deviceId: String,
    val fcmToken: String?,
)