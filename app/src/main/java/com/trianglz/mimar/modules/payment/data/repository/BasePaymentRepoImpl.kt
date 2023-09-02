package com.trianglz.mimar.modules.payment.data.repository

import com.trianglz.mimar.modules.payment.domain.model.PaymentType
import com.trianglz.mimar.modules.payment.domain.repository.BasePaymentRepo
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import javax.inject.Inject

@ActivityRetainedScoped
class BasePaymentRepoImpl @Inject constructor(): BasePaymentRepo {
    private val paymentFlow: MutableSharedFlow<PaymentType> = MutableSharedFlow()

    override fun getRequestPaymentStatusFlow(): Flow<PaymentType.RequestPaymentStatus> {
        return paymentFlow.filterIsInstance()
    }

    override suspend fun emitPaymentType(paymentType: PaymentType) {
        paymentFlow.emit(paymentType)
    }
}