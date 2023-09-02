package com.trianglz.mimar.modules.appointments.data.remote.data_source

import com.trianglz.mimar.modules.appointments.data.remote.model.AppointmentDataModel
import com.trianglz.mimar.modules.appointments.data.remote.retrofit.service.AppointmentsService
import kotlinx.coroutines.delay
import javax.inject.Inject

class AppointmentsRemoteDataSourceImpl @Inject constructor(private val service: AppointmentsService) :
    AppointmentsRemoteDataSource {

    override suspend fun getLastAppointment(): AppointmentDataModel? {
         return service.getLastAppointmentAsync().appointment
    }

    override suspend fun createAppointment(): AppointmentDataModel? {
        return service.createAppointmentAsync().appointment
    }
}