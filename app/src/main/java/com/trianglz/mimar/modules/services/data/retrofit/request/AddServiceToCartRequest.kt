package com.trianglz.mimar.modules.services.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class AddServiceToCartRequest (
    @Json(name = "branch_service_id")
    val branchServiceId: Int,
//    @Json(name = "customer_address_id")
//    val customerAddressId: Int?=null,
)