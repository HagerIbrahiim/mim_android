package com.trianglz.mimar.modules.services.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UpdateServiceRequest(
    @Json(name = "employee_did_not_show_up")
    val customerDidNotShowUp: Boolean? = null
)