package com.trianglz.mimar.modules.cart.domain.model


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ValidationMessageDomainModel(
    val id: Int? = null,
    val requiredAction: String? = null,
    val message: String? = null,
): Parcelable