package com.trianglz.mimar.modules.cart.presentation.model

sealed class CartBottomSheetType{
    object Address: CartBottomSheetType()
    object Note: CartBottomSheetType()
    data class ValidationMessage(val validationMessageUIModel: ValidationMessageUIModel): CartBottomSheetType()
}
