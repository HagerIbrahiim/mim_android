package com.trianglz.mimar.modules.currency.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CurrencyDataModel (
		@Json( name= "id")
		val id: Int,
		@Json( name= "name")
		val name: String,
		@Json( name= "symbol")
		val symbol: String
) 