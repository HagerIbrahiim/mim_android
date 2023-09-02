package com.trianglz.mimar.modules.addresses.ui.mapper

import android.content.Context
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel

fun CustomerAddressDomainModel.toUI(
    isChecked: Boolean = false,
) =
    CustomerAddressUIModel(
        id,
        StringWrapper(title),
        buildingNumber,
        city,
        isDefault,
        landmark,
        lat,
        lng,
        district,
        streetName,
        secondaryNum,
        isSupported = isSupported ?: true,
        isChecked = isChecked,
        country = country,

    )

fun CustomerAddressUIModel.toDomain(context: Context) =
    CustomerAddressDomainModel(
        id,
        title?.getString(context),
        buildingNumber,
        city,
        isDefault ?: false,
        landmark,
        lat,
        lng,
        district,
        streetName,
        secondaryNum,
        country = country
    )