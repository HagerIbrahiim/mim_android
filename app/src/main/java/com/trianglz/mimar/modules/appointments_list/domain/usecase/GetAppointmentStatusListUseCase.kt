package com.trianglz.mimar.modules.appointments_list.domain.usecase


import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.appointments_list.domain.model.AppointmentStatusDomainModel
import com.trianglz.mimar.modules.appointments_list.domain.repository.AppointmentsListRepository
import javax.inject.Inject

class GetAppointmentStatusListUseCase @Inject constructor(private val repo: AppointmentsListRepository) :
    BaseUseCase {
    suspend fun execute(): List<AppointmentStatusDomainModel>? {
        return repo.getAppointmentsStatusList()
    }
}