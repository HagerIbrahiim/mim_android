package com.trianglz.mimar.modules.addresses.data.mapper

import com.trianglz.mimar.modules.addresses.data.model.CustomerAddressDataModel
import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel

fun CustomerAddressDataModel.toDomain() = CustomerAddressDomainModel(
    id,title,buildingNumber, city, isDefault, landmark, lat, lng, district, streetName,secondaryNum, country,isSupported
)