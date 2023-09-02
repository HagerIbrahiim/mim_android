package com.trianglz.mimar.modules.countries.data.mapper

import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.utils.getAppLocale
import com.trianglz.mimar.modules.countries.data.model.CountryDataModel
import com.trianglz.mimar.modules.countries.domain.model.CountryDomainModel

fun CountryDataModel.toDomain()=
    CountryDomainModel(id, name,dialCode,shortCode,)