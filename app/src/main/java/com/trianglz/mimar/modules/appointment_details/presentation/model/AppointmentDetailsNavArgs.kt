package com.trianglz.mimar.modules.appointment_details.presentation.model

data class AppointmentDetailsNavArgs(
    val appointmentId: Int,
    val mode: AppointmentDetailsScreenMode = AppointmentDetailsScreenMode.ConfirmAppointment,
    val openReviewAppointment: Boolean = false
) {
    companion object {
        const val APPOINTMENT_ID = "appointmentId"
        const val OPEN_REVIEW_APPOINTMENT = "openReviewAppointment"
    }
}
