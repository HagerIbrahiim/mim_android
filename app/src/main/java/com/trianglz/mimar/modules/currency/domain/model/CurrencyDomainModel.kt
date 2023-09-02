package com.trianglz.mimar.modules.currency.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyDomainModel (
    val id: Int,
    val name: String,
    val symbol: String
): Parcelable