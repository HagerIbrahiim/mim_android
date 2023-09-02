package com.trianglz.mimar.modules.ratings.data.model

import com.squareup.moshi.Json

data class EmployeeRatingDataModel (
    @Json(name = "employee_id")
    val employeeId: Int,
    @Json(name = "rating")
    val rating: Int
)