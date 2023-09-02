package com.trianglz.mimar.modules.services.data.remote

import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentDetailsDataModel
import com.trianglz.mimar.modules.services.data.model.ServiceDataModel
import com.trianglz.mimar.modules.services.data.retrofit.request.UpdateServiceRequest
import com.trianglz.mimar.modules.services.data.retrofit.service.BranchServicesService
import javax.inject.Inject

class ServicesRemoteDataSourceImpl @Inject constructor(
    private val branchServicesService: BranchServicesService,
) : ServicesRemoteDataSource {
    override suspend fun getServicesList(
        page: Int,
        perPage: Int,
        searchString: String?,
        specialityId: Int?,
        branchId: Int?,
        offeredLocation: String?
        ): List<ServiceDataModel> {
       return branchServicesService.getBranchServicesListAsync(
           page = page,
           items = perPage,
           branchId = branchId,
           specialityId = specialityId,
           name = searchString,
           offeredLocation = offeredLocation
       ).branchServices ?: listOf()

    }

    override suspend fun updateAppointmentService(
        serviceId: Int,
        employeeDidNotShow: Boolean?
    ): AppointmentDetailsDataModel {
        return branchServicesService.updateAppointmentBranchServiceAsync(serviceId,UpdateServiceRequest(employeeDidNotShow))
            .appointment ?: AppointmentDetailsDataModel()
    }

}