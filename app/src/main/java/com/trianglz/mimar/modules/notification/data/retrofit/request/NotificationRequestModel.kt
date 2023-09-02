package com.trianglz.mimar.modules.notification.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class NotificationRequestModel(
    @Json(name = "notification") val notification: NotificationContentRequest,
    @Json(name = "data") val dataField: NotificationDataRequest?,
    @Json(name = "registration_ids") val registrationIds: List<String>?
)
