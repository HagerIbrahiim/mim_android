package com.trianglz.mimar.modules.notification.domain.manager

import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.trianglz.core.di.qualifiers.NotificationsScopeQualifier
import com.trianglz.mimar.modules.notification.domain.model.NotificationAction
import com.trianglz.mimar.modules.notification.domain.model.NotificationType
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityRetainedScoped
class ForegroundNotificationManager @Inject constructor(
    @NotificationsScopeQualifier val scope: CoroutineScope,
    private val notificationManager: NotificationManagerCompat
) {
    companion object {
        const val ACTION_ID = "action_id"
        const val CLICK_ACTION = "click_action"
    }

    private var mNotificationFlow: MutableSharedFlow<NotificationType> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val notificationFlow: SharedFlow<NotificationType>
        get() = mNotificationFlow.asSharedFlow()

    fun emitNotifications(intent: Intent?, isBackground: Boolean = false) {
        Log.d("ForegroundNotificationManager", ": scope = $scope")
        Log.d("ForegroundNotificationManager", ": mNotificationFlow = $mNotificationFlow")

        intent?.extras?.apply {
            val actionId = getString(ACTION_ID)
            when (val key = getString(CLICK_ACTION)) {
                NotificationAction.OpenAppointment.serverKey -> {
                    actionId?.toIntOrNull()?.let {
                        sendNotificationType(
                            NotificationType.NotificationTypeWithId.OpenAppointment(
                                key,
                                isBackground,
                                it
                            )
                        )
                    }
                }
                NotificationAction.OpenCart.serverKey -> {
                    sendNotificationType(
                        NotificationType.OpenCart(
                            key,
                            isBackground,
                        )
                    )
                }
                NotificationAction.SubmitAppointmentReview.serverKey -> {
                    actionId?.toIntOrNull()?.let {
                        sendNotificationType(
                            NotificationType.NotificationTypeWithId.SubmitAppointmentReview(
                                key,
                                isBackground,
                                it
                            )
                        )
                    }
                }

                /**
                 * The "InternetConnection"  is not a notification type.
                 * However, it has been included to take advantage of the notification flow,
                 * which can be triggered from anywhere in the application.
                 * This allows us to observe changes in the internet connection at any place within the app.
                 */
                NotificationAction.InternetConnection.serverKey -> {
                    sendNotificationType(
                        NotificationType.NetworkConnection(serverKey = key, isBackground = isBackground, isNetworkConnected =  actionId.toString())
                    )
                }
            }
        }
    }

    fun removeNotificationBanner(notificationType: NotificationType) {
        if (notificationType is NotificationType.NotificationTypeWithId) {
            notificationManager.cancel(
                "${notificationType.serverKey}_${notificationType.id}",
                notificationType.id.hashCode()
            )
        } else if (notificationType is NotificationType.NotificationTypeWithStringId) {
            notificationManager.cancel(
                "${notificationType.serverKey}_${notificationType.id}",
                notificationType.id.hashCode()
            )
        }

    }

    private fun sendNotificationType(type: NotificationType) {
        scope.launch {
            mNotificationFlow.emit(
                type
            )
        }
    }

    suspend fun handleNotificationAction(
        clickAction: String,
        actionId: String,
        extraId: String? = null
    ) {
//        when (clickAction) {
//
//        }
    }

}