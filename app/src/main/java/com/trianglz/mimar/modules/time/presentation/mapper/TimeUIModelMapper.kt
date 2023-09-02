package com.trianglz.mimar.modules.time.presentation.mapper

import androidx.compose.runtime.mutableStateOf
import com.trianglz.mimar.modules.time.domain.model.TimeDomainModel
import com.trianglz.mimar.modules.time.presentation.model.TimeUIModel

fun TimeDomainModel.toUIModel(onClick: (id: Int) -> Unit = {}): TimeUIModel {
    return TimeUIModel(
        id = id,
        title = title,
        isSelected = mutableStateOf(isSelected ?: false),
        onClick = onClick
    )
}