package com.trianglz.mimar.common.data.mapper

import com.trianglz.mimar.common.data.model.WorkingHoursDataModel
import com.trianglz.mimar.common.domain.model.WorkingHoursDomainModel

fun WorkingHoursDataModel.toDomain() =
    WorkingHoursDomainModel(id,weekDay ?:"",intervals?.map { it.toDomain() } )