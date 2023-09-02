package com.trianglz.mimar.modules.appointments.data.remote.mapper

import com.trianglz.mimar.modules.appointments.data.remote.model.AppointmentDataModel
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentDomainModel
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType.Companion.toAppointmentStatus
import com.trianglz.mimar.modules.branches.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.cart.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.toOfferedLocationType
import com.trianglz.mimar.modules.user.data.mapper.toDomainModel

fun AppointmentDataModel.toDomainModel() = AppointmentDomainModel(
    id = id,
    date = date,
    isCustomerConfirmed = isCustomerConfirmed ?: false,
    status = status.toAppointmentStatus(),
    location = location.toOfferedLocationType(),
    startsAt = startsAt,
    paymentMethod = paymentMethod,
    totalTime = totalEstimatedTime,
    appointmentBranchServices = appointmentBranchServices?.map { it.toDomainModel() },
    customer = customer?.toDomainModel(),
    branch = branch?.toDomainModel(),
    serviceProvider = branch?.toDomainModel(),
    appointmentNumber = appointmentNumber,
    branchName = branchName,
)