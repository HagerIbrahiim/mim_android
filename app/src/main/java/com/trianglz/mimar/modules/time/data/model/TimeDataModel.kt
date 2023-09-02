package com.trianglz.mimar.modules.time.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class TimeDataModel(
    @Json(name = "free_slot")
    val title: String?,
    @Json(name = "is_selected")
    val isSelected: Boolean?,
)
