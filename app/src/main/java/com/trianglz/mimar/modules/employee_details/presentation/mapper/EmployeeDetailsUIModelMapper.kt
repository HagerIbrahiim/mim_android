package com.trianglz.mimar.modules.employee_details.presentation.mapper

import com.trianglz.mimar.common.presentation.mapper.toUI
import com.trianglz.mimar.modules.employee_details.domain.model.EmployeeDetailsDomainModel
import com.trianglz.mimar.modules.employee_details.presentation.model.EmployeeDetailsUIModel
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel

fun EmployeeDetailsDomainModel.toUI(onAddServiceToCart: (ServiceUIModel) -> Unit) = EmployeeDetailsUIModel(
    rating,
    coveredZones,
    employeeNumber,
    employeeWorkingHours?.map { it.toUI() },
    firstName,
    id,
    image,
    isDeleted,
    lastName,
    nextAvailableSlot,
    offeredLocation,
    phoneNumber,
    services?.map { it.toUI(onAddServiceToCart) },
    userName
)