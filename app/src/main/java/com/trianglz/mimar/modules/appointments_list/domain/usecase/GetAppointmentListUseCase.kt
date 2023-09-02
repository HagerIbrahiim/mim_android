package com.trianglz.mimar.modules.appointments_list.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentDomainModel
import com.trianglz.mimar.modules.appointments_list.domain.repository.AppointmentsListRepository
import javax.inject.Inject

class GetAppointmentListUseCase @Inject constructor(private val repo: AppointmentsListRepository) :
    BaseUseCase {
    suspend fun execute(status: String?= null,page: Int, perPage: Int): List<AppointmentDomainModel>? {
        return repo.getAppointmentsList(status,page, perPage)
    }
}