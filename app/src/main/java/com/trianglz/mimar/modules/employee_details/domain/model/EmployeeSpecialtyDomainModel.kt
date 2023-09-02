package com.trianglz.mimar.modules.employee_details.domain.model

import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel


data class EmployeeSpecialtyDomainModel(
    val services: List<ServiceDomainModel>? = null,
    val specialty: SpecialtiesDomainModel? = null
)