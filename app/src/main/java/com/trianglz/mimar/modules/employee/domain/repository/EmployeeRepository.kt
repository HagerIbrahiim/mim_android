package com.trianglz.mimar.modules.employee.domain.repository

import com.trianglz.mimar.modules.employee.domain.model.EmployeeDomainModel

interface EmployeeRepository {


    suspend fun getEmployeeList(   page: Int?,
                                   perPage: Int?,
                                   branchId: Int?,
                                   cartBranchServiceId: Int?,
                                   date: String?,
                                   offeredLocation: String?,): List<EmployeeDomainModel>


}