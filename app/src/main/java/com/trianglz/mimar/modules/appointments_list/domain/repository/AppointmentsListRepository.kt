package com.trianglz.mimar.modules.appointments_list.domain.repository

import com.trianglz.mimar.modules.appointments.domain.model.AppointmentDomainModel
import com.trianglz.mimar.modules.appointments_list.domain.model.AppointmentStatusDomainModel

interface AppointmentsListRepository {
    suspend fun getAppointmentsList(status: String?= null,page: Int, perPage: Int): List<AppointmentDomainModel>?
    suspend fun getAppointmentsStatusList(): List<AppointmentStatusDomainModel>?
}