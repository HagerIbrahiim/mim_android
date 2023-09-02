package com.trianglz.mimar.modules.notification.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class NotificationContentRequest(
    @Json(name= "title") val title: String,
    @Json(name= "body") val body: String,
    @Json(name= "sound") val sound: String="default"
)
