package com.trianglz.mimar.common.data.mapper

import com.trianglz.mimar.common.data.model.WorkingHoursIntervalsDataModel
import com.trianglz.mimar.common.domain.model.WorkingHoursIntervalsDomainModel

fun WorkingHoursIntervalsDataModel.toDomain() =
    WorkingHoursIntervalsDomainModel(
        startsAt ?:"", endsAt ?:""
    )