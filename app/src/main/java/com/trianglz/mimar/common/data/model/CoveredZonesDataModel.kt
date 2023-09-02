package com.trianglz.mimar.common.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CoveredZonesDataModel(
    @Json(name = "id")
    val id: Int = -1,
    @Json(name = "name")
    val name: String? = "",
    @Json(name = "polygon")
    val polygon: List<CoveredZonesPolygonDataModel>? = null
)