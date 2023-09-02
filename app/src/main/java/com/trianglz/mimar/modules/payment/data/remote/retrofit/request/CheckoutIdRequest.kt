package com.trianglz.mimar.modules.payment.data.remote.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CheckoutIdRequest (
    @Json(name = "appointment_id") val appointmentId: Int,
    @Json(name = "payment_option") val paymentOption: String,
)