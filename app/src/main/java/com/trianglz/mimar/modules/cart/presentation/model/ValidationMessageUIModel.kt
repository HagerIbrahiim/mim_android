package com.trianglz.mimar.modules.cart.presentation.model


import android.os.Parcelable
import androidx.annotation.Keep
import com.trianglz.mimar.modules.cart.domain.model.ValidationMessageDomainModel
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ValidationMessageUIModel(
    val id: Int? = null,
    val requiredAction: CartValidationActionType? = null,
    val message: String? = null,
    val desc: String? = null,
): Parcelable