package com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UpdateAppointmentRequest (
    @Json(name = "status")
    val status: String?=null,
    @Json(name = "payment_method")
    val paymentMethod: String?=null,
    @Json(name = "customer_appointment_problem_reason_id")
    val appointmentProblemReasonId: Int?=null,
)