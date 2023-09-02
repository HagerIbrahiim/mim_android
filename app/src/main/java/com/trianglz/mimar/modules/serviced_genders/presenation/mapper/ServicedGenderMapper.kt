package com.trianglz.mimar.modules.serviced_genders.presenation.mapper

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderDomainModel
import com.trianglz.mimar.modules.serviced_genders.presenation.model.ServicedGenderUIModel

fun ServicedGenderDomainModel.toUI()= ServicedGenderUIModel(id,
    StringWrapper(name),value = value, isChecked = mutableStateOf(isChecked))

fun ServicedGenderUIModel.toDomain(context: Context) = ServicedGenderDomainModel(id, title.getString(context) , value, isChecked = isChecked.value)