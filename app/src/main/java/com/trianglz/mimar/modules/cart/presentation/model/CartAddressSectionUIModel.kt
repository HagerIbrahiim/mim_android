package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel

data class CartAddressSectionUIModel(
    val selectedAddress: State<CustomerAddressUIModel?>,
    val showSection: State<Boolean>,
): BaseCartUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
