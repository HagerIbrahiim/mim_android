package com.trianglz.mimar.modules.appointments_list.data.local.data_source

import com.trianglz.mimar.modules.appointments_list.data.local.model.AppointmentStatusDataModel
import javax.inject.Inject

class AppointmentsListLocalDataSourceImpl @Inject constructor() :
    AppointmentListLocalDataSource {
    override suspend fun getAppointmentsStatusList(): List<AppointmentStatusDataModel>? {
        return listOf(
            AppointmentStatusDataModel(0, "upcoming"),
            AppointmentStatusDataModel(1, "ongoing"),
            AppointmentStatusDataModel(2, "completed"),
            AppointmentStatusDataModel(3, "canceled")
        )
    }
}