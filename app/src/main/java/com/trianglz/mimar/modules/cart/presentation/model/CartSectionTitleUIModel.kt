package com.trianglz.mimar.modules.cart.presentation.model

import com.trianglz.core.domain.model.StringWrapper

data class CartSectionTitleUIModel(
    val title: StringWrapper
): BaseCartUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
