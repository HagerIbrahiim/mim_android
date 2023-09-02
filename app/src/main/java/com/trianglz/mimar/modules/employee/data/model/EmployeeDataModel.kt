package com.trianglz.mimar.modules.employee.data.model

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
open class EmployeeDataModel (
	@property:Json(name = "id")
	open var id: Int?=null,
	@property:Json(name = "username")
	open var userName: String?=null,
	@property:Json(name = "image")
	open var image: String? = null,
	@property:Json(name = "phone_number")
	open var phoneNumber: String? = null,
	@property:Json(name = "offered_location")
	open var offeredLocation: String? = null,
	@property:Json(name = "average_rating")
	open var rating: Double? = null,
	@property:Json(name = "next_available_slot")
	open var nextAvailableSlot: String? = null,
	@property:Json(name = "is_deleted")
	open var isDeleted: Boolean?=null,
	@property:Json(name = "is_anyone")
	open var isAnyone: Boolean?=null,
	)