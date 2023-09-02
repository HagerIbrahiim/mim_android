package com.trianglz.mimar.modules.appointments_list.data.remote.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.appointments_list.data.remote.retrofit.response.AppointmentListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AppointmentsListService {

    @GET(ApiPaths.APPOINTMENTS)
    suspend fun getAppointmentsListAsync(
        @Query(ApiQueries.PAGE) page: Int = 1,
        @Query(ApiQueries.ITEMS) items: Int = 10,
        @Query(ApiQueries.STATUS) status: String? = null,
        ): AppointmentListResponse
}
