package com.trianglz.mimar.common.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json;

@Keep
data class WorkingHoursDataModel(
    @Json(name = "id")
    val id: Int? = -1,
    @Json(name = "week_day")
    val weekDay: String? = "",
    @Json(name = "intervals")
    val intervals: List<WorkingHoursIntervalsDataModel>? = null
)


