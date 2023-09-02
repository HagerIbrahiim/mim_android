package com.trianglz.mimar.modules.addresses.data.retrofit.request

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class AddressRequestModel(
    @Json(name ="id")
    val addressId: Int? ,
    @Json(name ="title")
    val addressTitle: String,
    @Json(name ="city")
    val city: String,
    @Json(name ="street_name")
    val streetName: String,
    @Json(name ="building_number")
    val buildingNumber: String,
    @Json(name ="region")
    val district: String,
    @Json(name ="secondary_phone")
    val secondaryNumber: String?,
    @Json(name ="landmark")
    val landmark: String?,
    @Json(name ="lat")
    val lat: Double,
    @Json(name ="lng")
    val long: Double,
)

