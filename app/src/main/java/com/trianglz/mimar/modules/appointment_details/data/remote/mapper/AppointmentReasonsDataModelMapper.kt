package com.trianglz.mimar.modules.appointment_details.data.remote.mapper

import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentProblemReasonsDataModel
import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentProblemReasonsDomainModel

fun AppointmentProblemReasonsDataModel.toDomain() =
    AppointmentProblemReasonsDomainModel(id, reason)