package com.trianglz.mimar.modules.appointments.presentation.mapper

import com.trianglz.core.domain.enum.SimpleDateFormatEnum
import com.trianglz.core.domain.enum.SimpleDateFormatEnum.*
import com.trianglz.core.domain.extensions.formatDate
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentDomainModel
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentItemType
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentUIModel

fun AppointmentDomainModel.toUIModel(type: AppointmentItemType = AppointmentItemType.LastAppointment,onClick: (id: Int) -> Unit) = AppointmentUIModel(
    id = uniqueId,
    location = location,
    time = date.formatDate(returnFormat = SimpleDateFormatEnum.HOUR_MINUTES_12_FORMAT),
    status = status ?: AppointmentStatusType.Ongoing,
    isCustomerConfirmed = isCustomerConfirmed ?: false,
    date = date,
    appointmentNumber = appointmentNumber,
    branchName = branch?.name ?: branchName,
    type = type,
    startsAt = startsAt.formatDate(HOUR_MINUTES_24_FORMAT, HOUR_MINUTES_12_FORMAT),
    onClick = onClick,
)