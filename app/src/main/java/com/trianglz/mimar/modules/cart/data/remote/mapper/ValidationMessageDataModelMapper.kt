package com.trianglz.mimar.modules.cart.data.remote.mapper

import com.trianglz.mimar.modules.cart.data.remote.model.ValidationMessageDataModel
import com.trianglz.mimar.modules.cart.domain.model.ValidationMessageDomainModel

fun ValidationMessageDataModel.toDomain(): ValidationMessageDomainModel =
    ValidationMessageDomainModel(
        id = id, requiredAction = requiredAction, message = message
    )