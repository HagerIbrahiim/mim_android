package com.trianglz.mimar.modules.appointments.data.remote.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.appointments.data.remote.retrofit.response.AppointmentResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface AppointmentsService {
    //TODO LastAppointment Service
    @GET(ApiPaths.NEXT_APPOINTMENT)
    suspend fun getLastAppointmentAsync(): AppointmentResponse
    @POST(ApiPaths.APPOINTMENTS)
    suspend fun createAppointmentAsync(): AppointmentResponse

}
