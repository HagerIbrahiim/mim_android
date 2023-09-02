package com.trianglz.mimar.modules.offered_location.presentation.mapper

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationsDomainModel
import com.trianglz.mimar.modules.offered_location.presentation.model.OfferedLocationsUIModel

fun OfferedLocationsDomainModel.toUI() = OfferedLocationsUIModel(id,
    StringWrapper(name), value = value?:"", isChecked = mutableStateOf(isChecked))

fun OfferedLocationsUIModel.toDomain(context:Context) = OfferedLocationsDomainModel(id, title.getString(context),value, isChecked = isChecked.value)


