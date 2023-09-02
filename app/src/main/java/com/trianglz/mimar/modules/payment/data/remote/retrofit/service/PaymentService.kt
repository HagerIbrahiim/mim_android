package com.trianglz.mimar.modules.payment.data.remote.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.payment.data.remote.retrofit.request.CheckoutIdRequest
import com.trianglz.mimar.modules.payment.data.remote.retrofit.request.PaymentStatusRequest
import com.trianglz.mimar.modules.payment.data.remote.retrofit.response.CheckoutIdResponse
import com.trianglz.mimar.modules.payment.data.remote.retrofit.response.PaymentStatusResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentService {
    @POST(ApiPaths.REQUEST_CHECKOUT_ID)
    suspend fun getCheckoutIdAsync(
        @Body checkoutIdRequest: CheckoutIdRequest
    ): CheckoutIdResponse

    @POST(ApiPaths.VERIFY_PAYMENT)
    suspend fun getPaymentStatusAsync(
        @Body paymentStatusRequest: PaymentStatusRequest
    ): PaymentStatusResponse

}