package com.trianglz.mimar.modules.sign_in.data.retrofit.request


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class SocialLoginRequest(
    @Json(name = "access_token")
    val accessToken: String?,

    @Json(name = "device_id")
    val deviceId: String?,
    @Json(name = "fcm_token")
    val fcmToken: String?,
    @Json(name = "social_provider_id")
    val socialProviderId: String?,
    @Json(name = "social_provider_type")
    val socialProviderType: String?,
    @Json(name = "name")
    val name: String?,
)