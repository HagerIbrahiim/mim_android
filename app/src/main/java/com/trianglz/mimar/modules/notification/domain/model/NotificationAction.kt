package com.trianglz.mimar.modules.notification.domain.model

import com.trianglz.mimar.modules.home.presentation.HomeActivity.Companion.INTERNET_CONNECTION

sealed class NotificationAction(val serverKey: String) {
    object OpenAppointment : NotificationAction("view_appointment")
    object SubmitAppointmentReview : NotificationAction("submit_appointment_review")
    object OpenCart : NotificationAction("show_cart")
    object InternetConnection : NotificationAction(INTERNET_CONNECTION)

}

fun String.toNotificationAction() =
    when(this){
        NotificationAction.OpenAppointment.serverKey -> NotificationAction.OpenAppointment
        NotificationAction.SubmitAppointmentReview.serverKey -> NotificationAction.SubmitAppointmentReview
        else -> NotificationAction.OpenCart
    }


sealed class NotificationType(open val serverKey: String, open val isBackground: Boolean) {
    sealed class NotificationTypeWithId(
        override val serverKey: String,
        override val isBackground: Boolean,
        open val id: Int
    ) : NotificationType(serverKey, isBackground) {
        data class OpenAppointment(
            override val serverKey: String,
            override val isBackground: Boolean,
            override val id: Int
        ) : NotificationTypeWithId(serverKey, isBackground, id)

        data class SubmitAppointmentReview(
            override val serverKey: String,
            override val isBackground: Boolean,
            override val id: Int
        ) : NotificationTypeWithId(serverKey, isBackground, id)
    }

    sealed class NotificationTypeWithStringId(
        override val serverKey: String,
        override val isBackground: Boolean,
        open val id: String
    ) : NotificationType(serverKey, isBackground) {

    }

    data class OpenCart(override val serverKey: String, override val isBackground: Boolean) :
        NotificationType(serverKey, false)

    data class NetworkConnection(
        override val serverKey: String,
        override val isBackground: Boolean,
        val isNetworkConnected: String
    ) :
        NotificationTypeWithStringId(serverKey = serverKey, isBackground = false, id = isNetworkConnected)

    object Idle : NotificationType("", false)
}