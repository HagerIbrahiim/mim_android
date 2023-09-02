package com.trianglz.mimar.modules.appointments.domain.repository

import com.trianglz.mimar.modules.appointments.domain.model.AppointmentDomainModel

interface AppointmentsRepository {
    suspend fun getLastAppointment(): AppointmentDomainModel?
    suspend fun createAppointment(): AppointmentDomainModel?
}