package com.trianglz.mimar.modules.currency.data.mapper

import com.trianglz.mimar.modules.currency.data.model.CurrencyDataModel
import com.trianglz.mimar.modules.currency.domain.model.CurrencyDomainModel

fun CurrencyDataModel.toDomain()= CurrencyDomainModel(
    id, name, symbol
)