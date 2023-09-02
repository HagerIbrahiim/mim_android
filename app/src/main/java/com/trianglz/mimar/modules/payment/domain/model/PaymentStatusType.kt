package com.trianglz.mimar.modules.payment.domain.model

sealed class PaymentStatusType(open val key: String) {
    object Paid: PaymentStatusType("paid")
    object UnPaid: PaymentStatusType("unpaid")
}
fun String?.toPaymentStatusType(): PaymentStatusType {
    return when(this) {
        PaymentStatusType.Paid.key -> PaymentStatusType.Paid
        else -> PaymentStatusType.UnPaid
    }
}
