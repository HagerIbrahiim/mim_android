package com.trianglz.mimar.modules.addresses.ui.mapper

import com.trianglz.mimar.modules.addresses.domain.model.AddressInfoDomainModel
import com.trianglz.mimar.modules.addresses.ui.model.AddressInfoUIModel
import com.trianglz.mimar.modules.countries.presentation.mapper.toUI

fun AddressInfoDomainModel.toUI() = AddressInfoUIModel(
    country, city, region
)