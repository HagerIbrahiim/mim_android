package com.trianglz.mimar.modules.services.data.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.services.data.retrofit.response.BranchServicesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.response.AppointmentDetailsResponse
import com.trianglz.mimar.modules.services.data.retrofit.request.UpdateServiceRequest
import retrofit2.http.*

interface BranchServicesService {
    @GET(ApiPaths.BRANCH_SERVICES)
    suspend fun getBranchServicesListAsync(
        @Query(ApiQueries.PAGE) page: Int = 1,
        @Query(ApiQueries.ITEMS) items: Int = 10,
        @Query(ApiQueries.BRANCH_ID) branchId: Int? = null,
        @Query(ApiQueries.BRANCH_SPECIALITY_ID) specialityId: Int? = null,
        @Query(ApiQueries.TITLE) name: String? = null,
        @Query(ApiQueries.EMPLOYEE_ID) employeeId: Int? = null,
        @Query(ApiQueries.OFFERED_LOCATION) offeredLocation: String? = null,
        ): BranchServicesResponse

    @PATCH(ApiPaths.APPOINTMENT_BRANCH_SERVICE)
    suspend fun updateAppointmentBranchServiceAsync(
        @Path(ApiPaths.ID) serviceId: Int,
        @Body updateServiceRequest: UpdateServiceRequest,
    ): AppointmentDetailsResponse

}
