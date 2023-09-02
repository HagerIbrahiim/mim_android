package com.trianglz.mimar.modules.ratings.data.mapper

import com.trianglz.mimar.modules.ratings.data.model.EmployeeRatingDataModel
import com.trianglz.mimar.modules.ratings.domain.model.EmployeeRatingDomainModel

fun EmployeeRatingDomainModel.toData() = EmployeeRatingDataModel(
    employeeId, rating
)