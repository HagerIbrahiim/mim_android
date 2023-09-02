package com.trianglz.mimar.modules.payment.data.remote.mapper

import com.trianglz.mimar.modules.payment.data.remote.model.CheckoutIdDataModel
import com.trianglz.mimar.modules.payment.domain.model.CheckoutIdDomainModel

fun CheckoutIdDataModel.toDomain() = CheckoutIdDomainModel(
    checkoutId = checkoutId
)