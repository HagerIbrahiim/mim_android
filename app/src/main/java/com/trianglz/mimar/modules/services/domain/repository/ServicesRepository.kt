package com.trianglz.mimar.modules.services.domain.repository

import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentDetailsDomainModel
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import kotlinx.coroutines.flow.SharedFlow

interface ServicesRepository {

    suspend fun getServicesList(
        page: Int,
        perPage: Int,
        searchString: String?,
        specialityId: Int?,
        branchId: Int?,
        offeredLocation: String?
    ): List<ServiceDomainModel>
    suspend fun updateAppointmentService(
        serviceId: Int,
        employeeDidNotShow: Boolean? = null,
    ): AppointmentDetailsDomainModel


}