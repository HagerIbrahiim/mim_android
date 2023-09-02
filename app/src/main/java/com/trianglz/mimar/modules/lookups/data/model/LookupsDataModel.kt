package com.trianglz.mimar.modules.lookups.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class LookupsDataModel (
    @Json(name = "key")
    val key: String?=null,
    @Json(name = "value")
    val value: String?=null,
    @Json(name = "name")
    val name: String?=null,
)