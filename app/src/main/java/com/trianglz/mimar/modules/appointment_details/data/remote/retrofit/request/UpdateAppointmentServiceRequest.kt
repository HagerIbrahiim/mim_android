package com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UpdateAppointmentServiceRequest(
    @Json(name = "employee_did_not_show_up")
    val employeeDidNotShowUp: Boolean?=null
)