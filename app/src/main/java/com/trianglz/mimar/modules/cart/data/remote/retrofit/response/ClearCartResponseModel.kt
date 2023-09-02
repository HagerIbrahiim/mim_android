package com.trianglz.mimar.modules.cart.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.services.data.model.ServiceDataModel

@Keep
data class ClearCartResponseModel(
    @Json(name = "branch_services")
    val branchServices: List<ServiceDataModel>? = null,
): SuccessMessageResponse()