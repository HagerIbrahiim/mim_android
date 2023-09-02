package com.trianglz.mimar.modules.appointments_list.data.remote.data_source

import com.trianglz.mimar.modules.appointments.data.remote.model.AppointmentDataModel


interface AppointmentsListRemoteDataSource {
    suspend fun getAppointmentsList(status: String?= null,page: Int, perPage: Int): List<AppointmentDataModel>?

}