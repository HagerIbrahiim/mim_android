package com.trianglz.mimar.modules.services.data.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.services.data.model.ServiceDataModel

@Keep
data class UpdatedServiceResponseModel(
    @Json(name = "branch_service")
    val service: ServiceDataModel? = null,
): SuccessMessageResponse()