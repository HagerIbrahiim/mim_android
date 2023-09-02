package com.trianglz.mimar.modules.appointments_list.domain.model

import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType


data class AppointmentStatusDomainModel(
    val id: Int,
    val statusType: AppointmentStatusType,
)