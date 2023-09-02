package com.trianglz.mimar.modules.forgot_password.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class ForgotPasswordRequestModel(
    @Json(name = "phone_number")
    val phoneNumber: String,
    @Json(name = "dial_code")
    val dialCode: String,
)