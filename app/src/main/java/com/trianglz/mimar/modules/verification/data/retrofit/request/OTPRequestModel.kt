package com.trianglz.mimar.modules.verification.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class OTPRequestModel(
    @Json(name ="phone_verification_code")
    val phoneVerificationCode: String,
)