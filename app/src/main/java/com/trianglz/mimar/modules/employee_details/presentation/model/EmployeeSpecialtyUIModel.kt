package com.trianglz.mimar.modules.employee_details.presentation.model

import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel

data class EmployeeSpecialtyUIModel(
    val services: List<ServiceUIModel>? = null,
    val specialty: SpecialtiesUIModel? = null
)