package com.trianglz.mimar.modules.currency.presentation.model

import com.squareup.moshi.Json
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.utils.getAppLocale

data class CurrencyUIModel (
    @Json( name= "id")
    val id: Int,
    @Json( name= "name")
    val name: String,
    @Json( name= "symbol")
    val symbol: String
){
    val displayedCurrency =
        if (getAppLocale() == Locales.ARABIC.code) name
        else symbol
}