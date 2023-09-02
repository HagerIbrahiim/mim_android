package com.trianglz.mimar.modules.notification.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.notification.domain.model.NotificationDomainModel
import com.trianglz.mimar.modules.notification.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationsListUseCase @Inject constructor(private val repo: NotificationRepository) :
    BaseUseCase {
    suspend fun execute(page: Int, perPage: Int): List<NotificationDomainModel> {
        return repo.getNotifications(page, perPage)
    }
}