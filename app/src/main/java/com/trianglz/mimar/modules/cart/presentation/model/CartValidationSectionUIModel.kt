package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.State

data class CartValidationSectionUIModel(
    val validationMessageUIModel: State<ValidationMessageUIModel?>
): BaseCartUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
