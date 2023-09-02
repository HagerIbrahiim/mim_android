package com.trianglz.mimar.modules.notification.presentation

import android.app.Application
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.core_compose.presentation.pagination.model.ComposePaginationModel
import com.trianglz.mimar.common.presentation.base.MimarBaseViewModel
import com.trianglz.mimar.modules.notification.domain.model.NotificationAction
import com.trianglz.mimar.modules.notification.domain.usecase.SetNotificationSeenUseCase
import com.trianglz.mimar.modules.notification.presentation.contract.NotificationsListEvent
import com.trianglz.mimar.modules.notification.presentation.contract.NotificationsListEvent.NotificationItemClicked
import com.trianglz.mimar.modules.notification.presentation.contract.NotificationsListState
import com.trianglz.mimar.modules.notification.presentation.mapper.toUI
import com.trianglz.mimar.modules.notification.presentation.model.NotificationUIModel
import com.trianglz.mimar.modules.notification.presentation.source.NotificationsListSource
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.user.domain.usecase.SetUserUseCase
import com.trianglz.mimar.modules.user.presentaion.mapper.toDomain
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsListViewModel @Inject constructor(
    val source: NotificationsListSource,
    private val setNotificationSeenUseCase: SetNotificationSeenUseCase,
    val userModeHandler: UserModeHandler,
    private val setUserUseCase: SetUserUseCase,
    private val application: Application,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    ) : MimarBaseViewModel<NotificationsListEvent, BaseViewState, NotificationsListState>(getUserUpdatesUseCase) {

    init {
        startListenForUserUpdates()
        getNotifications()
        fillSourceData()
    }

    private fun resetNotificationsCount(user: UserUIModel) {
        launchCoroutine {
            setUserUseCase.execute(user = user.toDomain(application.baseContext).copy(unseenNotificationsCount = 0), false)
        }
    }

    override fun handleEvents(event: NotificationsListEvent) {
        when (event) {
            is NotificationItemClicked -> {
                handleNotificationClickAction(event.notification)
            }

            NotificationsListEvent.RefreshScreen -> {
                getNotifications()
            }
        }
    }

    private fun updateNotificationToSeen(notification: NotificationUIModel) {
        val list = source.dataListValue
        val index = list?.indexOfFirst { it.uniqueId == notification.id } ?: -1
        source.updateItem(
            position = index,
            item = notification,
            updateAction = ComposePaginationModel.UpdateAction.Update(true)
        )
    }

    private fun fillSourceData(){
        source.onNotificationClicked = {
            setEvent(NotificationItemClicked(it))
        }
    }
    private fun getNotifications() {
        source.refreshAll()
    }

    private fun handleNotificationClickAction(notification: NotificationUIModel) {
        handleClickAction(notification.clickAction, notification.actionId?.toIntOrNull())

        // Update notification if it is unseen yet
        launchCoroutine {
            if (notification.seen != true) {
                val updatedNotification =
                    setNotificationSeenUseCase.execute(notificationId = notification.id ?: -1).toUI{
                        setEvent(NotificationItemClicked(it))
                    }
                updateNotificationToSeen(updatedNotification)
            }
        }
    }

    private fun handleClickAction(clickAction: NotificationAction?, actionId: Int?) {
        when (clickAction) {
            NotificationAction.OpenAppointment -> {
                actionId?.let {
                    setState { NotificationsListState.OpenAppointmentDetails(it) }
                }
            }
            NotificationAction.OpenCart -> {
                setState { NotificationsListState.OpenCart }
            }
            else -> {
                actionId?.let {
                    setState { NotificationsListState.OpenAppointmentDetails(it, true) }
                }
            }

        }

    }

    override fun createInitialViewState(): BaseViewState? {
        return null
    }

    override fun userUpdates(user: UserUIModel) {
        if ((user.unseenNotificationsCount ?: 0) > 0) {
            resetNotificationsCount(user)

        }
    }
}