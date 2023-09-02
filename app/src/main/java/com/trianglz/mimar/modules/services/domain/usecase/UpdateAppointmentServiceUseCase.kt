package com.trianglz.mimar.modules.services.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentDetailsDomainModel
import com.trianglz.mimar.modules.services.domain.repository.ServicesRepository
import javax.inject.Inject

class UpdateAppointmentServiceUseCase @Inject constructor(private val repo: ServicesRepository) :
    BaseUseCase {
    suspend fun execute(
        serviceId: Int,
        employeeDidNotShow: Boolean? = null,
    ): AppointmentDetailsDomainModel {
        return repo.updateAppointmentService(serviceId, employeeDidNotShow)
    }
}