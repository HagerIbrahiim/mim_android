package com.trianglz.mimar.modules.sign_up.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class SignUpRequestModel(
    @Json(name = "phone_number")
    val phoneNumber: String,
    @Json(name = "dial_code")
    val dialCode: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "device_id")
    val deviceId: String,
    @Json(name = "fcm_token")
    val fcmToken: String?,
)