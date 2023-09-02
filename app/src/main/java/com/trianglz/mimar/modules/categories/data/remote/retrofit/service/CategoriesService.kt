package com.trianglz.mimar.modules.categories.data.remote.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.categories.data.remote.retrofit.response.CategoriesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoriesService {
    @GET(ApiPaths.CATEGORIES)
    suspend fun getCategoriesListAsync(
        @Query(ApiQueries.PAGE) page: Int = 1,
        @Query(ApiQueries.ITEMS) items: Int = 10,
    ): CategoriesListResponse
}
