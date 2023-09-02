package com.trianglz.mimar.modules.addresses.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class CustomerAddressDataModel (
	@Json(name ="id")
		val id: Int,
	@Json(name ="title")
		val title: String?= null,
	@Json(name ="building_number")
		val buildingNumber: String?= null,
	@Json(name = "city")
		val city: String?= null,
	@Json(name = "is_default")
		val isDefault: Boolean,
	@Json(name = "landmark")
		val landmark: String?= null,
	@Json(name = "lat")
		val lat: Double?= null,
	@Json(name = "lng")
		val lng: Double?= null,
	@Json(name = "region")
		val district: String?= null,
	@Json(name = "street_name")
		val streetName: String?= null,
	@Json(name = "secondary_phone")
	val secondaryNum: String?= null,
	@Json(name = "country")
		val country: String?= null,
	@Json(name = "is_supported")
	val isSupported: Boolean?= null,

)