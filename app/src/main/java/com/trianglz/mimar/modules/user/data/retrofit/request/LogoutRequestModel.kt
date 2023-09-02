package com.trianglz.mimar.modules.user.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json
@Keep
data class LogoutRequestModel(
    @Json(name = "device_id") val deviceId: String? = null
)
