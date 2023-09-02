package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.State
import com.trianglz.mimar.modules.currency.presentation.model.CurrencyUIModel

data class CartFooterSectionUIModel(
    val totalEstimatedPrice: State<Double?>,
    val totalEstimatedTime: State<String?>,
    val enableConfirmBtn: State<Boolean>,
    val currency: State<CurrencyUIModel?>,
): BaseCartUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
