package com.trianglz.mimar.common.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CoveredZonesPolygonDataModel(
    @Json(name = "lat")
    val lat: String? = "",
    @Json(name = "lng")
    val lng: String? = ""
)