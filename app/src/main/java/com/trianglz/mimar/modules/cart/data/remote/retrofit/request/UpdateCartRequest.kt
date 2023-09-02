package com.trianglz.mimar.modules.cart.data.remote.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UpdateCartRequest (
    @Json(name = "customer_address_id")
    val customerAddressId: Int?=null,
    @Json(name = "notes")
    val notes: String? = null,
    @Json(name = "appointment_location")
    val appointmentLocation: String? = null,
    @Json(name = "payment_method")
    val paymentMethod: String? = null,
    @Json(name = "first_service_time")
    val firstServiceTime: String? = null,
    @Json(name = "appointment_date")
    val appointmentDate: String? = null,
)