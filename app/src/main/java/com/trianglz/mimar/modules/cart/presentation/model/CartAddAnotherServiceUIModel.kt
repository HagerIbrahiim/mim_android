package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.State

data class CartAddAnotherServiceUIModel(
    val canAddAnotherService: State<Boolean>,
): BaseCartUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
