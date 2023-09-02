package com.trianglz.mimar.modules.appointments_list.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.appointments.data.remote.model.AppointmentDataModel

@Keep
class AppointmentListResponse(
    @Json(name = "appointments")
    val appointments: List<AppointmentDataModel>?
) : SuccessMessageResponse()