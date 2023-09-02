package com.trianglz.mimar.modules.employee_details.data.mapper

import com.trianglz.mimar.modules.employee_details.data.model.EmployeeSpecialtyDataModel
import com.trianglz.mimar.modules.employee_details.domain.model.EmployeeSpecialtyDomainModel
import com.trianglz.mimar.modules.services.data.mapper.toDomain
import com.trianglz.mimar.modules.specilaities.data.mapper.toDomain

fun EmployeeSpecialtyDataModel.toDomain() =
    EmployeeSpecialtyDomainModel(services?.map { it.toDomain() }, specialty?.toDomain())