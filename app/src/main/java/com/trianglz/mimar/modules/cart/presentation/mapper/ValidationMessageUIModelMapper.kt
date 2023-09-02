package com.trianglz.mimar.modules.cart.presentation.mapper

import com.trianglz.mimar.modules.cart.domain.model.ValidationMessageDomainModel
import com.trianglz.mimar.modules.cart.presentation.model.CartValidationActionType.Companion.toCartValidationActionType
import com.trianglz.mimar.modules.cart.presentation.model.ValidationMessageUIModel

fun ValidationMessageDomainModel.toUI(): ValidationMessageUIModel =
    ValidationMessageUIModel(
        id = id, requiredAction = requiredAction.toCartValidationActionType(), message = message
    )