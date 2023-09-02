package com.trianglz.mimar.modules.appointment_details.presentation.mapper

import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentProblemReasonsDomainModel
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentProblemReasonsUIModel

fun AppointmentProblemReasonsDomainModel.toUI() =
    AppointmentProblemReasonsUIModel(id ?: -1, reason)