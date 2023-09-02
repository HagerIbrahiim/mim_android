package com.trianglz.mimar.modules.appointment_details.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class AppointmentDetailsScreenMode : Parcelable {
    object AppointmentDetails: AppointmentDetailsScreenMode(), Parcelable
    object ConfirmAppointment: AppointmentDetailsScreenMode()
}