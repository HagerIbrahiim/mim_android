package com.trianglz.mimar.modules.payment.data.remote.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class PaymentStatusRequest (
    @Json(name = "checkout_id") val checkoutId: String,
    @Json(name = "appointment_id") val appointmentId: Int,
)