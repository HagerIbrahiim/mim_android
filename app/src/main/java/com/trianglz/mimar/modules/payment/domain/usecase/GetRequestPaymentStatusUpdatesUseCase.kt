package com.trianglz.mimar.modules.payment.domain.usecase

import com.trianglz.mimar.modules.payment.domain.model.PaymentType
import com.trianglz.mimar.modules.payment.domain.repository.BasePaymentRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRequestPaymentStatusUpdatesUseCase @Inject constructor(private val basePaymentRepo: BasePaymentRepo) {
    fun execute(): Flow<PaymentType.RequestPaymentStatus> {
        return basePaymentRepo.getRequestPaymentStatusFlow()
    }
}