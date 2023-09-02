package com.trianglz.mimar.modules.serviced_genders.data.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.lookups.data.retrofit.response.LookupsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicedGendersService {


    @GET(ApiPaths.LOOKUPS)
    suspend fun getServicedGendersListAsync(
        @Query(ApiQueries.GENDERS) gendersPage: Int?= null,
        ): LookupsListResponse

}
