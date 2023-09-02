package com.trianglz.mimar.modules.appointments_list.data.repository

import com.trianglz.mimar.modules.appointments.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentDomainModel
import com.trianglz.mimar.modules.appointments_list.data.local.data_source.AppointmentListLocalDataSource
import com.trianglz.mimar.modules.appointments_list.data.local.mapper.toDomain
import com.trianglz.mimar.modules.appointments_list.data.remote.data_source.AppointmentsListRemoteDataSource
import com.trianglz.mimar.modules.appointments_list.domain.model.AppointmentStatusDomainModel
import com.trianglz.mimar.modules.appointments_list.domain.repository.AppointmentsListRepository
import javax.inject.Inject

class AppointmentsListListRepositoryImpl @Inject constructor(
    private val remote: AppointmentsListRemoteDataSource,
    private val local: AppointmentListLocalDataSource
) : AppointmentsListRepository {
    override suspend fun getAppointmentsList(
        status: String?,
        page: Int,
        perPage: Int
    ): List<AppointmentDomainModel>? {
        return remote.getAppointmentsList(status, page, perPage)?.map { it.toDomainModel() }
    }

    override suspend fun getAppointmentsStatusList(): List<AppointmentStatusDomainModel>? {
        return local.getAppointmentsStatusList()?.map { it.toDomain() }
    }


}