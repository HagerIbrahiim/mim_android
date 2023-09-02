package com.trianglz.mimar.modules.payment.domain.usecase

import com.trianglz.mimar.modules.payment.domain.model.PaymentStatusDomainModel
import com.trianglz.mimar.modules.payment.domain.repository.PaymentRepo
import javax.inject.Inject

class GetPaymentStatusUseCase @Inject constructor(private val paymentRepo: PaymentRepo) {
    suspend fun execute(checkoutId: String, appointmentId: Int): PaymentStatusDomainModel {
        return paymentRepo.getPaymentStatus(checkoutId, appointmentId)
    }
}