package com.trianglz.mimar.modules.notification.domain.repository

import com.trianglz.mimar.modules.notification.domain.model.NotificationDomainModel

interface NotificationRepository {
    suspend fun getNotifications(page: Int, perPage: Int): List<NotificationDomainModel>
    suspend fun setNotificationSeen(notificationId: Int): NotificationDomainModel
    suspend fun clearAllNotifications()
    suspend fun sendNotification(notificationDomainModel: NotificationDomainModel)
}