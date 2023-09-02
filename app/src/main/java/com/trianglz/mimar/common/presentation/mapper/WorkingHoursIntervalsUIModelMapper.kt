package com.trianglz.mimar.common.presentation.mapper

import com.trianglz.mimar.common.domain.model.WorkingHoursIntervalsDomainModel
import com.trianglz.mimar.common.presentation.models.WorkingHoursIntervalsUIModel

fun WorkingHoursIntervalsDomainModel.toUI() =
    WorkingHoursIntervalsUIModel(
        startsAt, endsAt
    )