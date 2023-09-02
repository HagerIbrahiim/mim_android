package com.trianglz.mimar.modules.services.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CartBranchServicesAttributes(
    @Json(name = "branch_service_id")
    val branchServiceId: Int? = null,
    @Json(name = "destroy")
    val destroy: Boolean? = null
)