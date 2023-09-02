package com.trianglz.mimar.modules.notification.data.remote

import com.trianglz.mimar.modules.notification.data.model.NotificationDataModel
import com.trianglz.mimar.modules.notification.data.retrofit.request.NotificationRequestModel

interface NotificationRemoteDataSource {
    suspend fun getNotifications(page: Int, perPage: Int): List<NotificationDataModel>
    suspend fun setNotificationSeen(notificationId: Int): NotificationDataModel
    suspend fun clearAllNotifications()
    suspend fun sendNotification(model: NotificationRequestModel)
}