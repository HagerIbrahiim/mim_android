package com.trianglz.mimar.modules.employee.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.pagination.model.PaginatedModel
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType

open class EmployeeUIModel (
	open val id: Int?,
	open val userName: String= "",
	open val image: String? = null,
	open val phoneNumber: String? = null,
	open val offeredLocation: OfferedLocationType? = null,
	open val rating: Double? = null,
	open val nextAvailableSlot: String? = null,
	open val isDeleted: Boolean?=null,
	val isAvailable: Boolean? = null,
	open val isAnyone: Boolean? = null,
	override val showShimmer: Boolean = false,
//	open val hasConflict: Boolean? = null,
	var isChecked: MutableState<Boolean> = mutableStateOf(false),
	open val onClick : (Int) -> Unit = {},

	): PaginatedModel{

	open val availabilityText = if(isAvailable == true)
		StringWrapper{
			"${this.getString(R.string.available_at)} $nextAvailableSlot"
		} else StringWrapper(R.string.unavailable)


	override  val uniqueId: Int
		get() = id ?: -1
	companion object {
		fun getShimmerList(count: Int = 10): List<EmployeeUIModel> {
			val list: ArrayList<EmployeeUIModel> = ArrayList()
			repeat(count) {
				list.add(
					EmployeeUIModel(
						id = it,
						showShimmer = true,
						onClick = {}
					)
				)
			}
			return list
		}
	}


}