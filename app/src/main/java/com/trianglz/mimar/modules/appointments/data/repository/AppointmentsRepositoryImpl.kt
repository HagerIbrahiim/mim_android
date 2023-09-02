package com.trianglz.mimar.modules.appointments.data.repository

import com.trianglz.mimar.modules.appointments.data.remote.data_source.AppointmentsRemoteDataSource
import com.trianglz.mimar.modules.appointments.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentDomainModel
import com.trianglz.mimar.modules.appointments.domain.repository.AppointmentsRepository
import com.trianglz.mimar.modules.branches.data.remote.data_source.BranchesRemoteDataSource
import com.trianglz.mimar.modules.branches.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import javax.inject.Inject

class AppointmentsRepositoryImpl @Inject constructor(private val remote: AppointmentsRemoteDataSource):
    AppointmentsRepository {

    override suspend fun getLastAppointment(): AppointmentDomainModel? {
        return remote.getLastAppointment()?.toDomainModel()
    }

    override suspend fun createAppointment(): AppointmentDomainModel? {
        return remote.createAppointment()?.toDomainModel()
    }
}