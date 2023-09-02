package com.trianglz.mimar.modules.addresses.domain.model

data class EditAddAddressDomainModel(
    val addressTitle: String,
    val city: String,
    val streetName: String,
    val buildingNumber: String,
    val district: String,
    val secondaryNumber: String?,
    val landmark: String?,
    val lat: Double,
    val long: Double,
    val addressId: Int?= null,
)