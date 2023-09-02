package com.trianglz.mimar.modules.notification.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.notification.domain.model.NotificationDomainModel
import com.trianglz.mimar.modules.notification.domain.repository.NotificationRepository
import javax.inject.Inject

class SendNotificationUseCase @Inject constructor(private val notificationsRepository: NotificationRepository) :
    BaseUseCase {

    suspend fun execute(notificationDomainModel: NotificationDomainModel) {
        notificationsRepository.sendNotification(notificationDomainModel)
    }

}