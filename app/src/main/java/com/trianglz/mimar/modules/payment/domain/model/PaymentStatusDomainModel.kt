package com.trianglz.mimar.modules.payment.domain.model

import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentDetailsDomainModel

data class PaymentStatusDomainModel(
    val appointment: AppointmentDetailsDomainModel?
)
