package com.trianglz.mimar.modules.payment.domain.usecase

import com.trianglz.mimar.modules.payment.domain.model.PaymentType
import com.trianglz.mimar.modules.payment.domain.repository.BasePaymentRepo
import javax.inject.Inject

class EmitPaymentTypeUseCase @Inject constructor(private val basePaymentRepo: BasePaymentRepo) {
    suspend fun execute(paymentType: PaymentType) {
        return basePaymentRepo.emitPaymentType(paymentType)
    }
}