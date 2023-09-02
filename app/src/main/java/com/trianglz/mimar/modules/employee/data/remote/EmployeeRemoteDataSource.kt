package com.trianglz.mimar.modules.employee.data.remote

import com.trianglz.mimar.modules.employee.data.model.EmployeeDataModel

interface EmployeeRemoteDataSource {


    suspend fun getEmployeeList(page: Int?,
                                perPage: Int?,
                                branchId: Int?,
                                cartBranchServiceId: Int?,
                                date: String?,
                                offeredLocation: String?,): List<EmployeeDataModel>


}