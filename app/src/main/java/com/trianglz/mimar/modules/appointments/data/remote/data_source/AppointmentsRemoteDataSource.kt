package com.trianglz.mimar.modules.appointments.data.remote.data_source

import com.trianglz.mimar.modules.appointments.data.remote.model.AppointmentDataModel

interface AppointmentsRemoteDataSource {
    suspend fun getLastAppointment(): AppointmentDataModel?
    suspend fun createAppointment(): AppointmentDataModel?
}