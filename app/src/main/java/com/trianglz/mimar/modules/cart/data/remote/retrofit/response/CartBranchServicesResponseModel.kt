package com.trianglz.mimar.modules.cart.data.remote.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.cart.data.remote.model.CartBranchServicesDataModel

@Keep
data class CartBranchServicesResponseModel(
    @Json(name = "branch_service")
    val branchService: CartBranchServicesDataModel? = null,
): SuccessMessageResponse()