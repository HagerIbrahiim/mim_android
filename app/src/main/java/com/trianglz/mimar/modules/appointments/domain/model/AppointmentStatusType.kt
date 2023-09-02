package com.trianglz.mimar.modules.appointments.domain.model

import com.trianglz.mimar.R


sealed class AppointmentStatusType(val key: String, val name: Int) {
    object Upcoming: AppointmentStatusType("upcoming", R.string.upcoming)
    object Completed: AppointmentStatusType("completed",R.string.completed)
    object Ongoing: AppointmentStatusType("ongoing",R.string.ongoing)
    object PendingPayment: AppointmentStatusType("pending_payment",R.string.pending_payment)
    object Cancelled: AppointmentStatusType("canceled",R.string.cancelled)

    companion object {
        fun String?.toAppointmentStatus(): AppointmentStatusType {
            return when(this) {
                Completed.key -> Completed
                Ongoing.key -> Ongoing
                Cancelled.key -> Cancelled
                PendingPayment.key -> PendingPayment
                else -> Upcoming
            }
        }
    }
}