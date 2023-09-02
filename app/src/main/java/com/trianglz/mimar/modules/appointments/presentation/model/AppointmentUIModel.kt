package com.trianglz.mimar.modules.appointments.presentation.model

import com.trianglz.core_compose.presentation.pagination.model.PaginatedModel
import com.trianglz.core_compose.presentation.pagination.model.ShimmerModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType

open class AppointmentUIModel(
    open val id: Int = -1,
    open val appointmentNumber: String? = null,
    open val time: String? = null,
    open val branchName: String? = null,
    open val isCustomerConfirmed: Boolean = false,
    open val location: OfferedLocationType? = null,
    open val status: AppointmentStatusType? = AppointmentStatusType.Ongoing,
    open val date: String? = null,
    open val startsAt: String?=null,
    val type: AppointmentItemType?= AppointmentItemType.LastAppointment,
    override val showShimmer: Boolean = false,
    val onClick: (id: Int) -> Unit = {}
) : ShimmerModel, PaginatedModel {
    override val uniqueId: Int
        get() = id


    companion object {
        fun getShimmerList(count: Int = 10): List<AppointmentUIModel> {
            val list: ArrayList<AppointmentUIModel> = ArrayList()
            repeat(count) {
                list.add(
                    AppointmentUIModel(
                        id = it,
                        showShimmer = true,
                        startsAt = "12:10",
                        appointmentNumber = "number",
                        onClick = {}
                    )
                )
            }
            return list
        }
    }
}
