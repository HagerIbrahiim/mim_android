package com.trianglz.mimar.modules.payment.presentation.mapper

import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.payment.domain.model.PaymentMethodType
import com.trianglz.mimar.modules.payment.domain.model.toPaymentMethodType
import com.trianglz.mimar.modules.payment.presentation.model.PaymentMethodUIModel

fun PaymentMethodType.toUIModel(isSelected: Boolean = false): PaymentMethodUIModel {
    return PaymentMethodUIModel(
        id = id,
        key = key,
        title = StringWrapper(displayName),
        isChecked = mutableStateOf(isSelected),
        showShimmer = false,
        icon = icon
    )
}

fun PaymentMethodUIModel.toPaymentType(): PaymentMethodType {
    return when(this.key) {
         PaymentMethodType.Cash.key -> PaymentMethodType.Cash
         else -> PaymentMethodType.Online()
    }
}