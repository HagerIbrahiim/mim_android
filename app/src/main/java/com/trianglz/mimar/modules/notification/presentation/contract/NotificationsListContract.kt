package com.trianglz.mimar.modules.notification.presentation.contract

import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.mimar.modules.notification.presentation.model.NotificationUIModel

// Events that user performed
sealed class NotificationsListEvent : BaseEvent {
    object RefreshScreen: NotificationsListEvent()
    data class NotificationItemClicked(val notification: NotificationUIModel) : NotificationsListEvent()

}

sealed class NotificationsListState: BaseState {
    data class OpenAppointmentDetails(val id: Int, val openReviewBottomSheet: Boolean?= false): NotificationsListState()
    object OpenCart: NotificationsListState()
}