package com.trianglz.mimar.modules.appointments.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentDomainModel
import com.trianglz.mimar.modules.appointments.domain.repository.AppointmentsRepository
import javax.inject.Inject

class GetLastAppointmentUseCase @Inject constructor(private val repo: AppointmentsRepository) :
    BaseUseCase {
    suspend fun execute(): AppointmentDomainModel? {
        return repo.getLastAppointment()
    }
}