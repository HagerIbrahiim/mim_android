package com.trianglz.mimar.modules.appointment_details.data.remote.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class AppointmentProblemReasonsDataModel(
    @Json(name = "id")
    val id: Int? = -1,
    @Json(name = "reason")
    val reason: String? = ""
)