package com.trianglz.mimar.modules.employee.data.mapper

import com.trianglz.mimar.modules.employee.data.model.EmployeeDataModel
import com.trianglz.mimar.modules.employee.domain.model.EmployeeDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.toOfferedLocationType

fun EmployeeDataModel.toDomain() = EmployeeDomainModel(
    id,
    userName ?: "",
    image,
    phoneNumber,
    offeredLocation.toOfferedLocationType(),
    rating,
    nextAvailableSlot,
    isDeleted,
    isAnyone
)