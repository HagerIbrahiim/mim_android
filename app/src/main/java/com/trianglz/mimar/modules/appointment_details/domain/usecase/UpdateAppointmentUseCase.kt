package com.trianglz.mimar.modules.appointment_details.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentDetailsDomainModel
import com.trianglz.mimar.modules.appointment_details.domain.repository.AppointmentDetailsRepository
import javax.inject.Inject

class UpdateAppointmentUseCase @Inject constructor(private val repo: AppointmentDetailsRepository) :
    BaseUseCase {
    suspend fun execute(
        appointmentId: Int,
        status: String? = null,
        paymentMethod: String? = null,
        appointmentProblemReasonId: Int?=null,
    ): AppointmentDetailsDomainModel {
        return repo.updateAppointment(
            appointmentId,
            status,
            paymentMethod,
            appointmentProblemReasonId
        )
    }
}