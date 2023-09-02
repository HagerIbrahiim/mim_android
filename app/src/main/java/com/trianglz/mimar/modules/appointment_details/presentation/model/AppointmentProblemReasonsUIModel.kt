package com.trianglz.mimar.modules.appointment_details.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel

data class AppointmentProblemReasonsUIModel (
	override val id: Int= -1,
	val reason: String?="",
	override val isChecked: MutableState<Boolean> = mutableStateOf(false),
	override val value: String = "",
	override val showShimmer: Boolean = false
): BaseCheckboxItemUiModel {
	override val title: StringWrapper
		get() = StringWrapper(reason)

}