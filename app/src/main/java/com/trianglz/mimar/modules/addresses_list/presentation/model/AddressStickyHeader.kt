package com.trianglz.mimar.modules.addresses_list.presentation.model

import com.trianglz.core_compose.presentation.pagination.model.StickyModel

data class AddressStickyHeader(val show: Boolean) : StickyModel, BaseAddressModel{
    override val uniqueId: Int
        get() = System.identityHashCode(this)
    override val showShimmer: Boolean
        get() = false
}