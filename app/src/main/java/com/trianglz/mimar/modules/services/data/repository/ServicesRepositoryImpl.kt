package com.trianglz.mimar.modules.services.data.repository

import com.trianglz.mimar.modules.appointment_details.data.remote.mapper.toDomain
import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentDetailsDomainModel
import com.trianglz.mimar.modules.services.data.mapper.toDomain
import com.trianglz.mimar.modules.services.data.remote.ServicesRemoteDataSource
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.services.domain.repository.ServicesRepository
import javax.inject.Inject

class ServicesRepositoryImpl @Inject constructor(private val remote: ServicesRemoteDataSource) :
    ServicesRepository {

    override suspend fun getServicesList(
        page: Int,
        perPage: Int,
        searchString: String?,
        specialityId: Int?,
        branchId: Int?,
        offeredLocation: String?
    ): List<ServiceDomainModel> {
        return remote.getServicesList(
            page = page,
            perPage = perPage,
            searchString = searchString,
            specialityId = specialityId,
            branchId = branchId,
            offeredLocation = offeredLocation
        ).map { it.toDomain() }
    }

    override suspend fun updateAppointmentService(
        serviceId: Int,
        employeeDidNotShow: Boolean?
    ): AppointmentDetailsDomainModel {
        return remote.updateAppointmentService(serviceId, employeeDidNotShow).toDomain()
    }


}