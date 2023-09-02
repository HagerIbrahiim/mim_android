package com.trianglz.mimar.modules.currency.presentation.mapper

import com.trianglz.mimar.modules.currency.domain.model.CurrencyDomainModel
import com.trianglz.mimar.modules.currency.presentation.model.CurrencyUIModel

fun CurrencyDomainModel.toUI()= CurrencyUIModel(
    id, name, symbol
)

fun CurrencyUIModel.toDomain()= CurrencyDomainModel(
    id, name, symbol
)