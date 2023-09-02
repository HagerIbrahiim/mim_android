package com.trianglz.mimar.modules.addresses.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomerAddressDomainModel (
	val id: Int,
	val title: String?= null,
	val buildingNumber: String?= null,
	val city: String?= null,
	val isDefault: Boolean,
	val landmark: String?= null,
	val lat: Double?= null,
	val lng: Double?= null,
	val district: String?= null,
	val streetName: String?= null,
	val secondaryNum: String?= null,
	val country: String?= null,
	val isSupported: Boolean?= null,
	): Parcelable