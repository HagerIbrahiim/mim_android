package com.trianglz.mimar.modules.notification.presentation.mapper

import com.trianglz.mimar.modules.notification.domain.model.NotificationDomainModel
import com.trianglz.mimar.modules.notification.domain.model.toNotificationAction
import com.trianglz.mimar.modules.notification.presentation.model.NotificationUIModel

fun NotificationDomainModel.toUI(onNotificationClicked: (NotificationUIModel) -> Unit) = NotificationUIModel(
    actionId = actionId,
    body = body,
    clickAction = clickAction?.toNotificationAction(),
    createdAt = createdAt,
    id = id,
    seen = seen,
    title = title,
    onNotificationClicked = onNotificationClicked
)