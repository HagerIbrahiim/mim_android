package com.trianglz.mimar.modules.services.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UpdateBranchServiceInCartRequest (
    @Json(name = "employee_id")
    val employeeId: Int?,
    @Json(name = "is_anyone")
    val isAnyone: Boolean?,
)