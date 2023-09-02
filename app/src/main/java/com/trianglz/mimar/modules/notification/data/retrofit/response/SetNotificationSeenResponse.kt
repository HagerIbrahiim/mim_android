package com.trianglz.mimar.modules.notification.data.retrofit.response

import androidx.annotation.Keep
import com.trianglz.mimar.modules.notification.data.model.NotificationDataModel
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse

@Keep
data class SetNotificationSeenResponse (
    @Json(name = "notification") val notificationDataModel: NotificationDataModel
): SuccessMessageResponse()