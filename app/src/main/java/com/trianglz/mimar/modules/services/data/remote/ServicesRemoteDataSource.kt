package com.trianglz.mimar.modules.services.data.remote

import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentDetailsDataModel
import com.trianglz.mimar.modules.services.data.model.ServiceDataModel

interface ServicesRemoteDataSource {

    suspend fun getServicesList(
        page: Int,
        perPage: Int,
        searchString: String?,
        specialityId: Int?=null,
        branchId: Int? = null,
        offeredLocation: String? = null
    ): List<ServiceDataModel>

    suspend fun updateAppointmentService(
        serviceId: Int,
        employeeDidNotShow: Boolean? = null,
    ): AppointmentDetailsDataModel

}