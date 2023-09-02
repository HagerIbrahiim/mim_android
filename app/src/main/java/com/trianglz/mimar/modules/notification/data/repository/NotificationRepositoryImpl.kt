package com.trianglz.mimar.modules.notification.data.repository

import com.trianglz.mimar.modules.notification.data.mapper.toDomainModel
import com.trianglz.mimar.modules.notification.data.mapper.toRequestModel
import com.trianglz.mimar.modules.notification.data.remote.NotificationRemoteDataSource
import com.trianglz.mimar.modules.notification.domain.model.NotificationDomainModel
import com.trianglz.mimar.modules.notification.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(private val remoteDataSource: NotificationRemoteDataSource) :
    NotificationRepository {
    override suspend fun getNotifications(
        page: Int,
        perPage: Int
    ): List<NotificationDomainModel> {
        return remoteDataSource.getNotifications(page, perPage).map { it.toDomainModel() }
    }

    override suspend fun setNotificationSeen(notificationId: Int): NotificationDomainModel {
        return remoteDataSource.setNotificationSeen(notificationId).toDomainModel()
    }

    override suspend fun clearAllNotifications() {
       remoteDataSource.clearAllNotifications()
    }

    override suspend fun sendNotification(notificationDomainModel: NotificationDomainModel) {
        remoteDataSource.sendNotification(notificationDomainModel.toRequestModel())
    }
}