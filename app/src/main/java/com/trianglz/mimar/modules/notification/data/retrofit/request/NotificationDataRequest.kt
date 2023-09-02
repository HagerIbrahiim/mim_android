package com.trianglz.mimar.modules.notification.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class NotificationDataRequest(
    @Json(name= "action_id") val threadId: String?,
    @Json(name= "click_action") val clickAction: String?
)
