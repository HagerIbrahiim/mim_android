package com.trianglz.mimar.modules.appointment_details.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.appointment_details.domain.repository.AppointmentDetailsRepository
import javax.inject.Inject

class GetAppointmentCancellationPolicyUseCase @Inject constructor(private val repo: AppointmentDetailsRepository) :
    BaseUseCase {
    suspend fun execute(): String {
        return repo.getAppointmentCancellationPolicy()
    }
}