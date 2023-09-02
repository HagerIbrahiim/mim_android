package com.trianglz.mimar.modules.appointments.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.appointments.data.remote.model.AppointmentDataModel

@Keep
class AppointmentResponse(
    @Json(name = "appointment")
    val appointment: AppointmentDataModel?
) : SuccessMessageResponse()