package com.trianglz.mimar.modules.sign_in.domain.model


import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class SocialLoginDomainModel(
    val accessToken: String?,
    val deviceId: String?,
    val fcmToken: String?,
    val socialProviderId: String?,
    val socialProviderType: String?,
    val name: String?,
    )