package com.trianglz.mimar.modules.employee_details.data.retrofit.service

import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.employee_details.data.retrofit.response.EmployeeDetailsResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeDetailsService {

    @GET(ApiPaths.EMPLOYEES_DETAILS)
    suspend fun getEmployeeDetails(
        @Path(ApiPaths.ID) id: Int,
    ): EmployeeDetailsResponseModel

}