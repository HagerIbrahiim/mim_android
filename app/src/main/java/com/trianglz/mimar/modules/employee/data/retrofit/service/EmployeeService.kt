package com.trianglz.mimar.modules.employee.data.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.employee.data.retrofit.response.EmployeeListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EmployeeService {

    @GET(ApiPaths.EMPLOYEES)
    suspend fun getEmployeeListAsync(
        @Query(ApiQueries.PAGE) page: Int? = -1,
        @Query(ApiQueries.ITEMS) items: Int? = 10,
        @Query(ApiQueries.BRANCH_ID) branchId: Int? = null,
        @Query(ApiQueries.CART_SERVICE_ID) cartBranchId: Int? =null,
        @Query(ApiQueries.DATE) date: String? =null,
        @Query(ApiQueries.OFFERED_LOCATION) offeredLocation: String? =null,

        ): EmployeeListResponse

}
