package com.trianglz.mimar.modules.services.data.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.services.data.model.ServiceDataModel
import com.trianglz.mimar.modules.specilaities.data.model.SpecialtiesDataModel

@Keep
class BranchServicesResponse(
    @Json(name = "branch_services")
    val branchServices: List<ServiceDataModel>?
) : SuccessMessageResponse()