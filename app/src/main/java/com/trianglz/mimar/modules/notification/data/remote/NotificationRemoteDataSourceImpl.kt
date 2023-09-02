package com.trianglz.mimar.modules.notification.data.remote

import com.trianglz.mimar.modules.notification.data.model.NotificationDataModel
import com.trianglz.mimar.modules.notification.data.retrofit.request.NotificationRequestModel
import com.trianglz.mimar.modules.notification.data.retrofit.service.NotificationService
import com.trianglz.mimar.modules.notification.data.utils.NotificationsConstants.NOTIFICATIONS_URL
import kotlinx.coroutines.delay
import javax.inject.Inject

class NotificationRemoteDataSourceImpl @Inject constructor(private val service: NotificationService) :
    NotificationRemoteDataSource {
    override suspend fun getNotifications(page: Int, perPage: Int): List<NotificationDataModel> {
        return service.getUpdatesApiService(page, perPage).notifications
    }

    override suspend fun setNotificationSeen(notificationId: Int): NotificationDataModel {
         return service.updateNotification(notificationId).notificationDataModel
    }

    override suspend fun clearAllNotifications() {
        delay(1000)
//        service.clearAllNotifications()
    }

    override suspend fun sendNotification(model: NotificationRequestModel) {
        service.sendNotification(NOTIFICATIONS_URL, model)
    }
}