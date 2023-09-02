package com.trianglz.mimar.modules.employee.domain.model

import android.os.Parcelable
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import kotlinx.parcelize.Parcelize

@Parcelize
open class EmployeeDomainModel (
	open val id: Int?,
	open val userName: String,
	open val image: String? = null,
	open val phoneNumber: String?= null,
	open val offeredLocation: OfferedLocationType?=null,
	open val rating: Double?=null,
	open val nextAvailableSlot: String? = null,
	open val isDeleted: Boolean? = null,
	open val isAnyone: Boolean? = null,

	): Parcelable