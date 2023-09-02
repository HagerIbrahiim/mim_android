package com.trianglz.mimar.modules.payment.data.repository

import com.trianglz.mimar.modules.payment.data.remote.data_source.PaymentRemoteDataSource
import com.trianglz.mimar.modules.payment.data.remote.mapper.toDomain
import com.trianglz.mimar.modules.payment.domain.model.CheckoutIdDomainModel
import com.trianglz.mimar.modules.payment.domain.model.PaymentStatusDomainModel
import com.trianglz.mimar.modules.payment.domain.repository.PaymentRepo
import javax.inject.Inject

class PaymentRepoImpl @Inject constructor(private val paymentRemoteDataSource: PaymentRemoteDataSource) :
    PaymentRepo {
    override suspend fun getCheckoutId(
        appointmentId: Int,
        paymentOption: String,
    ): CheckoutIdDomainModel? {
        return paymentRemoteDataSource.getCheckoutId(appointmentId,paymentOption)?.toDomain()
    }

    override suspend fun getPaymentStatus(
        checkoutId: String,
        appointmentId: Int
    ): PaymentStatusDomainModel {
        return paymentRemoteDataSource.getPaymentStatus(checkoutId, appointmentId).toDomain()
    }
}