package com.trianglz.mimar.modules.countries.data.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.countries.data.retrofit.response.CountriesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CountriesApisService {
    @GET(ApiPaths.COUNTRIES)
    suspend fun getCountriesListAsync(
        @Query(ApiQueries.PAGE) page: Int = 1,
        @Query(ApiQueries.ITEMS) items: Int = 10,
        @Query(ApiQueries.NAME) searchString: String? = null,

        ): CountriesListResponse

}
