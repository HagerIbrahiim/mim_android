package com.trianglz.mimar.modules.cart.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class CartValidationActionType(val key: String): Parcelable {
    object ChangeData: CartValidationActionType("change_date")
    object ChangeEmployee: CartValidationActionType("change_employee")


    object ChangeTime: CartValidationActionType("change_time")

    companion object{
        fun String?.toCartValidationActionType(): CartValidationActionType? {
            return when(this) {
                ChangeData.key -> ChangeData
                ChangeEmployee.key -> ChangeEmployee
                ChangeTime.key -> ChangeTime
                else -> null
            }
        }
    }
}
