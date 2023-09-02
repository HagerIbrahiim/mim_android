package com.trianglz.mimar.modules.specilaities.data.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.specilaities.data.model.BranchSpecialitiesDataModel

@Keep
class BranchSpecialtiesListResponse(
    @Json(name = "branch_specialties")
    val specialties: List<BranchSpecialitiesDataModel>?
) : SuccessMessageResponse()