package com.trianglz.mimar.modules.offered_location.data.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.lookups.data.retrofit.response.LookupsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OfferedLocationService {


    @GET(ApiPaths.LOOKUPS)
    suspend fun getOfferedLocationListAsync(
        @Query(ApiQueries.OFFERED_LOCATIONS) offeredLocationPage: Int?= null,
        ): LookupsListResponse

}
