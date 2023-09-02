package com.trianglz.mimar.modules.appointments.presentation.model

sealed class AppointmentItemType {
    object AppointmentList: AppointmentItemType()
    object LastAppointment: AppointmentItemType()
}