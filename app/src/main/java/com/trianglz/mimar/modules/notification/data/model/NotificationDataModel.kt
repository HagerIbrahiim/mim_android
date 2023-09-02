package com.trianglz.mimar.modules.notification.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class NotificationDataModel(
    @Json(name = "action_id")
    val actionId: String?=null,
    @Json(name = "body")
    val body: String?=null,
    @Json(name = "click_action")
    val clickAction: String?=null,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "id")
    val id: Int?=null,
    @Json(name = "is_seen")
    val seen: Boolean?=null,
    @Json(name = "title")
    val title: String?= null,
)