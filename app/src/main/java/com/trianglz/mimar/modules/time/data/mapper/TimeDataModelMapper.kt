package com.trianglz.mimar.modules.time.data.mapper

import com.trianglz.mimar.modules.time.data.model.TimeDataModel
import com.trianglz.mimar.modules.time.domain.model.TimeDomainModel

fun TimeDataModel.toDomainModel() = TimeDomainModel(
    id = System.identityHashCode(this), title = title ?: "", isSelected = isSelected
)