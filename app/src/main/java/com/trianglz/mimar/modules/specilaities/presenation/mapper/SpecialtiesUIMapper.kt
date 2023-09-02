package com.trianglz.mimar.modules.specilaities.presenation.mapper

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel

fun SpecialtiesDomainModel.toUI()
= SpecialtiesUIModel(id, StringWrapper(name), mutableStateOf(isChecked))

fun SpecialtiesUIModel.toDomain(context: Context) = SpecialtiesDomainModel(id,title.getString(context) , isChecked.value)