package com.trianglz.mimar.modules.appointments_list.data.local.data_source

import com.trianglz.mimar.modules.appointments_list.data.local.model.AppointmentStatusDataModel

interface AppointmentListLocalDataSource {
    suspend fun getAppointmentsStatusList(): List<AppointmentStatusDataModel>?
}