package com.trianglz.mimar.modules.notification.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.notification.domain.repository.NotificationRepository
import javax.inject.Inject

class ClearAllNotificationsUseCase @Inject constructor(private val repo: NotificationRepository) :
    BaseUseCase {
    suspend fun execute() {
        repo.clearAllNotifications()
    }
}