package com.trianglz.mimar.modules.notification.data.mapper

import com.trianglz.mimar.modules.notification.data.model.NotificationDataModel
import com.trianglz.mimar.modules.notification.data.retrofit.request.NotificationContentRequest
import com.trianglz.mimar.modules.notification.data.retrofit.request.NotificationDataRequest
import com.trianglz.mimar.modules.notification.data.retrofit.request.NotificationRequestModel
import com.trianglz.mimar.modules.notification.domain.model.NotificationDomainModel

fun NotificationDataModel.toDomainModel() = NotificationDomainModel(
    actionId = actionId,
    body = body,
    clickAction = clickAction,
    createdAt = createdAt,
    id = id,
    seen = seen,
    title = title,
)


fun NotificationDomainModel.toRequestModel(): NotificationRequestModel {
    return NotificationRequestModel(
        notification = NotificationContentRequest(
            title ?: "", body ?: ""
        ),
        dataField = NotificationDataRequest(
            actionId, clickAction
        ),
        registrationIds = registeredIds
    )
}