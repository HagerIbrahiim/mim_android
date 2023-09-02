package com.trianglz.mimar.common.presentation.mapper

import com.trianglz.mimar.common.domain.model.WorkingHoursDomainModel
import com.trianglz.mimar.common.presentation.models.WorkingHoursUIModel

fun WorkingHoursDomainModel.toUI() =
    WorkingHoursUIModel(id,weekDay,intervals?.map { it.toUI() })