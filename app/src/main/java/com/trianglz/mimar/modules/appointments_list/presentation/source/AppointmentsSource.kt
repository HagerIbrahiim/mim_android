package com.trianglz.mimar.modules.appointments_list.presentation.source

import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.modules.appointments.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentItemType
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentUIModel
import com.trianglz.mimar.modules.appointments_list.domain.usecase.GetAppointmentListUseCase
import javax.inject.Inject

class AppointmentsSource @Inject constructor(
    private val getAppointmentListUseCase: GetAppointmentListUseCase
) : ComposePaginatedListDataSource<AppointmentUIModel>(
    autoInit = true,
    shimmerList = AppointmentUIModel.getShimmerList()
) {

    var status: String? = null
    var onClick: ((id: Int) -> Unit) = { }
    override suspend fun getPage(page: Int, perPage: Int): List<AppointmentUIModel> {
        if(status == null) return AppointmentUIModel.getShimmerList()
        return getAppointmentListUseCase.execute(status, page, perPage)?.map { it.toUIModel(
            type = AppointmentItemType.AppointmentList,
            onClick = onClick) }
            ?: listOf()
    }
}
