package com.trianglz.mimar.modules.time.data.remote.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.time.data.model.TimeDataModel

@Keep
class TimeSlotsListResponse(
    @Json(name = "free_slots")
    val timeSlots: List<TimeDataModel>?
) : SuccessMessageResponse()