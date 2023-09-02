package com.trianglz.mimar.modules.appointments_list.data.remote.data_source

import com.trianglz.mimar.modules.appointments.data.remote.model.AppointmentDataModel
import com.trianglz.mimar.modules.appointments_list.data.remote.retrofit.service.AppointmentsListService
import javax.inject.Inject

class AppointmentsListRemoteDataSourceImpl @Inject constructor(private val service: AppointmentsListService) :
    AppointmentsListRemoteDataSource {
    override suspend fun getAppointmentsList(status: String?,page: Int, perPage: Int): List<AppointmentDataModel>? {
        return service.getAppointmentsListAsync(page,perPage,status).appointments
    }


}