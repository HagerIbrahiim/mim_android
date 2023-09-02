package com.trianglz.mimar.modules.payment.data.remote.mapper

import com.trianglz.mimar.modules.appointment_details.data.remote.mapper.toDomain
import com.trianglz.mimar.modules.appointments.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.payment.data.remote.retrofit.response.PaymentStatusResponse
import com.trianglz.mimar.modules.payment.domain.model.PaymentStatusDomainModel

fun PaymentStatusResponse.toDomain() = PaymentStatusDomainModel(
    appointment = appointment?.toDomain()
)