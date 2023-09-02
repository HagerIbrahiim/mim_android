package com.trianglz.mimar.modules.addresses.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json


@Keep
data class AddressInfoDataModel(
    @Json(name = "country")
    val country: String? = null,
    @Json(name = "city")
    val city: String? = null,
    @Json(name = "region")
    val region: String?= null
)
