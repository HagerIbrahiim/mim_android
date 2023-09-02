package com.trianglz.mimar.common.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class WorkingHoursIntervalsDataModel(
    @Json(name = "starts_at")
    val startsAt: String? = "",
    @Json(name = "ends_at")
    val endsAt: String? = ""
)