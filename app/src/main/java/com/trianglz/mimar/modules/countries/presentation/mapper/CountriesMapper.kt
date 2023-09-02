package com.trianglz.mimar.modules.countries.presentation.mapper

import com.trianglz.mimar.modules.countries.domain.model.CountryDomainModel
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel

fun CountryDomainModel.toUI() = CountryUIModel(
    id, name, dialCode, shortCode,
)

fun CountryUIModel.toDomain() = CountryDomainModel(
    id, name, dialCode, shortCode,
)