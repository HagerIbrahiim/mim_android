package com.trianglz.mimar.modules.countries.data.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.countries.data.model.CountryDataModel
@Keep
data class CountriesListResponse (
    @Json(name = "countries")
    val countries: List<CountryDataModel>
): SuccessMessageResponse()