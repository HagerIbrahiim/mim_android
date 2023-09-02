package com.trianglz.mimar.modules.add_address.presentation.model

import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.map.presentation.model.MapScreenMode

data class AddAddressNavArgs(
    val addressInfo: CustomerAddressUIModel? = null,
    val mode: MapScreenMode = MapScreenMode.AddAddress,
    val fromHome: Boolean? = null,
)