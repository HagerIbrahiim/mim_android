package com.trianglz.mimar.modules.specilaities.data.mapper

import com.trianglz.mimar.modules.specilaities.data.model.BranchSpecialitiesDataModel
import com.trianglz.mimar.modules.specilaities.data.model.SpecialtiesDataModel
import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel

fun SpecialtiesDataModel.toDomain() = SpecialtiesDomainModel(id, title, isChecked)

fun SpecialtiesDomainModel.toData()= SpecialtiesDataModel(id, name, isChecked)

fun BranchSpecialitiesDataModel.toDomain() = SpecialtiesDomainModel(id.toIntOrNull() ?: -1, title, isChecked)
