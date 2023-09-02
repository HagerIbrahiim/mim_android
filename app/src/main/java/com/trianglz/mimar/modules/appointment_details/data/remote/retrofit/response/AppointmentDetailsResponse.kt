package com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentDetailsDataModel

@Keep
data class AppointmentDetailsResponse (
    @Json(name = "appointment")
    val appointment: AppointmentDetailsDataModel? = null
): SuccessMessageResponse()