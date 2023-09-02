package com.trianglz.mimar.modules.employee_details.data.model


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.mimar.common.data.model.CoveredZonesDataModel
import com.trianglz.mimar.common.data.model.WorkingHoursDataModel
import com.trianglz.mimar.modules.employee.data.model.EmployeeDataModel

@Keep
data class EmployeeDetailsDataModel(
    @Json(name = "covered_zones")
    val coveredZones: List<CoveredZonesDataModel>? = null,
    @Json(name = "employee_number")
    val employeeNumber: String? = null,
    @Json(name = "employee_working_hours")
    val employeeWorkingHours: List<WorkingHoursDataModel>? = null,
    @Json(name = "first_name")
    val firstName: String? = null,
    @Json(name = "last_name")
    val lastName: String? = null,
    @Json(name = "specialties")
    val services: List<EmployeeSpecialtyDataModel>? = null,
) : EmployeeDataModel()