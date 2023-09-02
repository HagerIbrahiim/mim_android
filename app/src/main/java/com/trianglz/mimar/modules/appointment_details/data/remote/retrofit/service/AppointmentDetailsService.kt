package com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.request.UpdateAppointmentRequest
import com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.response.AppointmentCancellationPolicyResponse
import com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.response.AppointmentDetailsResponse
import com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.response.AppointmentProblemReasonsListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path


interface AppointmentDetailsService {
    @GET(ApiPaths.APPOINTMENT_Details)
    suspend fun getAppointmentDetailsAsync(
        @Path(ApiPaths.ID) id: Int,
    ): AppointmentDetailsResponse

    @PATCH(ApiPaths.APPOINTMENT_Details)
    suspend fun updateAppointment(
        @Path(ApiPaths.ID) appointmentId: Int,
        @Body updateAppointmentRequest: UpdateAppointmentRequest
    ): AppointmentDetailsResponse

    @GET(ApiPaths.APPOINTMENT_PROBLEM_REASONS)
    suspend fun getAppointmentProblemReasonsListAsync(
    ): AppointmentProblemReasonsListResponse

    @GET(ApiPaths.CANCELLATION_POLICY)
    suspend fun getAppointmentCancellationPolicyAsync(
    ): AppointmentCancellationPolicyResponse

}
