package com.trianglz.mimar.modules.notification.presentation.source

import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.modules.notification.domain.usecase.GetNotificationsListUseCase
import com.trianglz.mimar.modules.notification.presentation.mapper.toUI
import com.trianglz.mimar.modules.notification.presentation.model.NotificationUIModel
import javax.inject.Inject

class NotificationsListSource @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsListUseCase
) : ComposePaginatedListDataSource<NotificationUIModel>(
    autoInit = false,
    shimmerList = NotificationUIModel.getShimmerList()
) {
    var onNotificationClicked: (NotificationUIModel) -> Unit = {}
    override suspend fun getPage(page: Int, perPage: Int): List<NotificationUIModel> {
        return getNotificationsUseCase.execute(page, perPage).map { it.toUI(onNotificationClicked) }
    }
}
