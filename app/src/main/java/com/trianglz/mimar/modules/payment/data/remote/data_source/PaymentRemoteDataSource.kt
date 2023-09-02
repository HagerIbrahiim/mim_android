package com.trianglz.mimar.modules.payment.data.remote.data_source

import com.trianglz.mimar.modules.payment.data.remote.model.CheckoutIdDataModel
import com.trianglz.mimar.modules.payment.data.remote.retrofit.response.PaymentStatusResponse

interface PaymentRemoteDataSource {
    suspend fun getCheckoutId(appointmentId:Int, paymentOption:String): CheckoutIdDataModel?
    suspend fun getPaymentStatus(checkoutId: String, appointmentId: Int): PaymentStatusResponse
}