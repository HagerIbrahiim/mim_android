package com.trianglz.mimar.modules.appointments_list.presentation.mapper

import com.trianglz.mimar.modules.appointments_list.domain.model.AppointmentStatusDomainModel
import com.trianglz.mimar.modules.appointments_list.presentation.model.AppointmentStatusUIModel

fun AppointmentStatusDomainModel.toUI(onClick: ((Int) -> Unit)?)= AppointmentStatusUIModel(
    uniqueId = id, statusType = statusType, onClick = onClick
)