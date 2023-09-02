package com.trianglz.mimar.modules.payment.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentDetailsDataModel
import com.trianglz.mimar.modules.appointments.data.remote.model.AppointmentDataModel

@Keep
data class PaymentStatusResponse(
    @Json(name = "appointment")
    var appointment: AppointmentDetailsDataModel?,

) : SuccessMessageResponse()
