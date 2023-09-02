package com.trianglz.mimar.modules.employee_details.data.mapper

import com.trianglz.mimar.common.data.mapper.toDomain
import com.trianglz.mimar.modules.employee_details.data.model.EmployeeDetailsDataModel
import com.trianglz.mimar.modules.employee_details.domain.model.EmployeeDetailsDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.toOfferedLocationType

fun EmployeeDetailsDataModel.toDomain() = EmployeeDetailsDomainModel(
    rating,
    coveredZones?.map { it.toDomain() },
    employeeNumber,
    employeeWorkingHours?.map { it.toDomain() },
    firstName,
    id,
    image,
    isDeleted,
    lastName,
    nextAvailableSlot,
    offeredLocation.toOfferedLocationType(),
    phoneNumber,
    services?.map { it.toDomain() },
    userName ?:""
)