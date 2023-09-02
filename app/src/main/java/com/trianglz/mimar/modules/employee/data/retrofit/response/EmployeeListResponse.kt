package com.trianglz.mimar.modules.employee.data.retrofit.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.modules.employee.data.model.EmployeeDataModel

@Keep
class EmployeeListResponse(
    @Json(name = "employees")
    val employees: List<EmployeeDataModel>?
) : SuccessMessageResponse()