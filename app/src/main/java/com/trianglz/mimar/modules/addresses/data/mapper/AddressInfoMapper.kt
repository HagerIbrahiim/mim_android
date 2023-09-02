package com.trianglz.mimar.modules.addresses.data.mapper

import com.trianglz.mimar.modules.addresses.data.model.AddressInfoDataModel
import com.trianglz.mimar.modules.addresses.domain.model.AddressInfoDomainModel
import com.trianglz.mimar.modules.countries.data.mapper.toDomain

fun AddressInfoDataModel.toDomain() = AddressInfoDomainModel(
    country,city, region
)