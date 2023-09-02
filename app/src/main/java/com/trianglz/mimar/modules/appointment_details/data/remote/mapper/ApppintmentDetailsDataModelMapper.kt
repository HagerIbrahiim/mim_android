package com.trianglz.mimar.modules.appointment_details.data.remote.mapper

import com.trianglz.mimar.modules.addresses.data.mapper.toDomain
import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentDetailsDataModel
import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentDetailsDomainModel
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType.Companion.toAppointmentStatus
import com.trianglz.mimar.modules.branches.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.cart.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.currency.data.mapper.toDomain
import com.trianglz.mimar.modules.offered_location.domain.model.toOfferedLocationType
import com.trianglz.mimar.modules.payment.domain.model.toPaymentStatusType

fun AppointmentDetailsDataModel.toDomain() = AppointmentDetailsDomainModel(
    id,
    status.toAppointmentStatus(),
    date,
    location.toOfferedLocationType(),
    branchName,
    appointmentBranchServices?.map { it.toDomainModel() },
    paymentMethod,
    totalEstimatedTime,
    appointmentNumber,
    branch?.toDomainModel(),
    canCancel,
    currency?.toDomain(),
    paymentStatus.toPaymentStatusType(),
    paymentOption,
    cancellerId,
    cancellerType,
    allowedPaymentMethods,
    employeeAppointmentProblemReason,
    customerAppointmentProblemReason,
    hasReview,
    totalExactFees,
    totalExactFeesWithoutVat,
    vat,
    vatAmount,
    customerAddress?.toDomain(),
)