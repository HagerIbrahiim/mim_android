package com.trianglz.mimar.modules.appointments_list.data.local.mapper

import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType.Companion.toAppointmentStatus
import com.trianglz.mimar.modules.appointments_list.data.local.model.AppointmentStatusDataModel
import com.trianglz.mimar.modules.appointments_list.domain.model.AppointmentStatusDomainModel

fun AppointmentStatusDataModel.toDomain() = AppointmentStatusDomainModel(
    id, name.toAppointmentStatus()
)