package com.trianglz.mimar.modules.employee.data.remote

import com.trianglz.mimar.modules.employee.data.model.EmployeeDataModel
import com.trianglz.mimar.modules.employee.data.retrofit.service.EmployeeService
import javax.inject.Inject

class EmployeeRemoteDataSourceImpl @Inject constructor(
    private val employeeService: EmployeeService
) :
    EmployeeRemoteDataSource {
    override suspend fun getEmployeeList(page: Int?,
                                         perPage: Int?,
                                         branchId: Int?,
                                         cartBranchServiceId: Int?,
                                         date: String?,
                                         offeredLocation: String?,): List<EmployeeDataModel> {
       return  employeeService.getEmployeeListAsync(page,perPage,branchId,cartBranchServiceId,date, offeredLocation).employees ?: listOf()
    }


}
