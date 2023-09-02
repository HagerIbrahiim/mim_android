package com.trianglz.mimar.modules.appointments.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentDomainModel
import com.trianglz.mimar.modules.appointments.domain.repository.AppointmentsRepository
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import javax.inject.Inject

class CreateAppointmentUseCase @Inject constructor(private val repo: AppointmentsRepository, private val cartRepo: CartRepository) :
    BaseUseCase {
    suspend fun execute(): AppointmentDomainModel? {
        val appointment = repo.createAppointment()
        appointment?.appointmentBranchServices?.let { cartRepo.updateServices(it) }
        return appointment
    }
}