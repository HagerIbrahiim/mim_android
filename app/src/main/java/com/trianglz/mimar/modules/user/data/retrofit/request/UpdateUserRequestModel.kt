package com.trianglz.mimar.modules.user.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UpdateUserRequestModel(
    @Json(name ="image")
    val image: String? = null,
    @Json(name ="first_name")
    val firstName: String? = null,
    @Json(name ="last_name")
    val lastName: String? = null,
    @Json(name ="email")
    val email: String? = null,
    @Json(name ="date_of_birth")
    val dateOfBirth: String? = null,
    @Json(name ="gender")
    val gender: String? = null,
    @Json(name ="device_id")
    val deviceId: String? = null,
    @Json(name ="fcm_token")
    val fcmToken: String? = null,
    @Json(name ="dial_code")
    val dialCode: String? = null,
    @Json(name ="phone_number")
    val phoneNumber: String? = null,
    @Json(name = "is_notifiable")
    val pushNotification: Boolean? = null,

    )
