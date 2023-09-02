package com.trianglz.mimar.modules.specilaities.data.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.specilaities.data.retrofit.response.BranchSpecialtiesListResponse
import com.trianglz.mimar.modules.specilaities.data.retrofit.response.SpecialtiesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpecialtiesService {
    @GET(ApiPaths.SPECIALITIES)
    suspend fun getSpecialtiesListAsync(
        @Query(ApiQueries.PAGE) page: Int = -1,
        @Query(ApiQueries.BRANCH_ID) branchId: Int? = null,
        ): SpecialtiesListResponse

    @GET(ApiPaths.BRANCH_SPECIALITIES)
    suspend fun getBranchSpecialtiesListAsync(
        @Query(ApiQueries.PAGE) page: Int = -1,
        @Query(ApiQueries.BRANCH_ID) branchId: Int? = null,
    ): BranchSpecialtiesListResponse
}
