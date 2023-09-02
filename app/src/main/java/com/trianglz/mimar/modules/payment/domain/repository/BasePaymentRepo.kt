package com.trianglz.mimar.modules.payment.domain.repository

import com.trianglz.mimar.modules.payment.domain.model.PaymentType
import kotlinx.coroutines.flow.Flow

interface BasePaymentRepo {
    fun getRequestPaymentStatusFlow(): Flow<PaymentType.RequestPaymentStatus>
    suspend fun emitPaymentType(paymentType: PaymentType)
}