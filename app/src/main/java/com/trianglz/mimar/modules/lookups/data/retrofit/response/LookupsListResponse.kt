package com.trianglz.mimar.modules.lookups.data.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.lookups.data.model.LookupsListDataModel

@Keep
data class LookupsListResponse (
    @Json(name = "data")
    val data: List<LookupsListDataModel>?= null
): SuccessMessageResponse()