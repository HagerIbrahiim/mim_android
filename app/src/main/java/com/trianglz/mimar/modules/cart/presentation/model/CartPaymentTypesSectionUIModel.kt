package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.mimar.modules.payment.presentation.model.PaymentMethodUIModel

data class CartPaymentTypesSectionUIModel(
    val paymentMethods: SnapshotStateList<PaymentMethodUIModel?>
): BaseCartUIModel {
    override val uniqueId: Int
        get() = System.identityHashCode(this)
}
