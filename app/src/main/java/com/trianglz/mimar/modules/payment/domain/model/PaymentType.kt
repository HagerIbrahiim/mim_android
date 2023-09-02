package com.trianglz.mimar.modules.payment.domain.model

sealed class PaymentType {
    data class RequestPaymentStatus(val checkoutId: String): PaymentType()
}
