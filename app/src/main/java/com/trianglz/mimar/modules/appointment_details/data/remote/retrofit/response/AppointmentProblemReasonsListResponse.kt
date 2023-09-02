package com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.response

import com.squareup.moshi.Json
import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentProblemReasonsDataModel

data class AppointmentProblemReasonsListResponse(
    @Json(name = "customer_appointment_problem_reasons")
    val appointmentProblemReasons: List<AppointmentProblemReasonsDataModel>?=null
)