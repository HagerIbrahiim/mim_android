package com.trianglz.mimar.modules.payment.data.remote.data_source

import com.trianglz.mimar.modules.payment.data.remote.model.CheckoutIdDataModel
import com.trianglz.mimar.modules.payment.data.remote.retrofit.request.CheckoutIdRequest
import com.trianglz.mimar.modules.payment.data.remote.retrofit.request.PaymentStatusRequest
import com.trianglz.mimar.modules.payment.data.remote.retrofit.response.PaymentStatusResponse
import com.trianglz.mimar.modules.payment.data.remote.retrofit.service.PaymentService
import javax.inject.Inject

class PaymentRemoteDataSourceImpl @Inject constructor(private val paymentService: PaymentService) :
    PaymentRemoteDataSource {
    override suspend fun getCheckoutId(
        appointmentId: Int,
        paymentOption: String,
    ): CheckoutIdDataModel? {
        return paymentService.getCheckoutIdAsync(CheckoutIdRequest(appointmentId,paymentOption)).checkoutId
    }

    override suspend fun getPaymentStatus(
        checkoutId: String,
        appointmentId: Int
    ): PaymentStatusResponse {
        return paymentService.getPaymentStatusAsync(PaymentStatusRequest(checkoutId, appointmentId))
    }
}