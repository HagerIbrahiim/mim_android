package com.trianglz.mimar.modules.addresses.data.mapper

import com.trianglz.mimar.modules.addresses.data.retrofit.request.AddressRequestModel
import com.trianglz.mimar.modules.addresses.domain.model.EditAddAddressDomainModel

fun EditAddAddressDomainModel.toRequest() = AddressRequestModel(
    addressId,
    addressTitle,
    city,
    streetName,
    buildingNumber,
    district,
    secondaryNumber,
    landmark,
    lat,
    long,

)