package com.trianglz.mimar.modules.user.domain.model

data class UpdateUserDomainModel(
    val image: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val dateOfBirth: String? = null,
    val gender: String? = null,
    val deviceId: String? = null,
    val fcmToken: String? = null,
    val dialCode: String? = null,
    val phoneNumber: String? = null,
    val pushNotification: Boolean? = null,
)
