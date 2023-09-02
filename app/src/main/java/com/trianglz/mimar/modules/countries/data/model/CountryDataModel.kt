package com.trianglz.mimar.modules.countries.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CountryDataModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "dial_code")
    val dialCode: String,
    @Json(name = "short_code")
    val shortCode: String,
    @Json(name = "name")
    val name: String
)