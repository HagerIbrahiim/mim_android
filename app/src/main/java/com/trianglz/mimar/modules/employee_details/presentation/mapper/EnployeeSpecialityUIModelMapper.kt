package com.trianglz.mimar.modules.employee_details.presentation.mapper

import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.employee_details.domain.model.EmployeeSpecialtyDomainModel
import com.trianglz.mimar.modules.employee_details.presentation.model.EmployeeSpecialtyUIModel
import com.trianglz.mimar.modules.services.presentation.mapper.toUI
import com.trianglz.mimar.modules.services.presentation.model.ServiceType
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import com.trianglz.mimar.modules.specilaities.presenation.mapper.toUI

fun EmployeeSpecialtyDomainModel.toUI(onAddServiceToCart: (ServiceUIModel) -> Unit) =
    EmployeeSpecialtyUIModel(
        services?.map { it.toUI(onServiceItemClicked = null, onAddServiceToCart = onAddServiceToCart,
            type = ServiceType.EmployeeService) },
        specialty?.toUI()
    )

fun List<EmployeeSpecialtyUIModel>.convertSpecialitiesToMap(): Map<StringWrapper?, List<ServiceUIModel>> {
    return this.mapNotNull { item ->
        item.specialty?.title?.let { title ->
            title to item.services.orEmpty()
        }
    }.toMap()
}