package com.trianglz.mimar.modules.specilaities.data.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.specilaities.data.model.SpecialtiesDataModel

@Keep
class SpecialtiesListResponse(
    @Json(name = "specialties")
    val specialties: List<SpecialtiesDataModel>?
) : SuccessMessageResponse()