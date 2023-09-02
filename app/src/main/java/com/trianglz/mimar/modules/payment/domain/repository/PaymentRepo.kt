package com.trianglz.mimar.modules.payment.domain.repository

import com.trianglz.mimar.modules.payment.domain.model.CheckoutIdDomainModel
import com.trianglz.mimar.modules.payment.domain.model.PaymentStatusDomainModel

interface PaymentRepo {
    suspend fun getCheckoutId(appointmentId:Int, paymentOption:String): CheckoutIdDomainModel?
    suspend fun getPaymentStatus(checkoutId: String, appointmentId: Int): PaymentStatusDomainModel
}