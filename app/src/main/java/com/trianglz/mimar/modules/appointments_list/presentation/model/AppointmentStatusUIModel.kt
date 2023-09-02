package com.trianglz.mimar.modules.appointments_list.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.common.presentation.selectables.model.SelectableUIModel
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType

data class AppointmentStatusUIModel(
    override val showShimmer: Boolean = false,
    override val uniqueId: Int,
    override val isChecked: MutableState<Boolean> = mutableStateOf(false),
    val statusType: AppointmentStatusType?= null,
    val onClick: ((Int) -> Unit)?
): SelectableUIModel{

    override val title: StringWrapper
        get() = StringWrapper(statusType?.name)

    companion object {
        fun getShimmerList(count: Int = 10): List<AppointmentStatusUIModel> {
            val list: ArrayList<AppointmentStatusUIModel> = ArrayList()
            repeat(count) {
                list.add(
                    AppointmentStatusUIModel(
                        uniqueId = it,
                        showShimmer = true,

                        onClick = {}
                    )
                )
            }
            return list
        }
    }


}