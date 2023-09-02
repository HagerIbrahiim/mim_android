package com.trianglz.mimar.modules.employee_details.data.data_source

import com.trianglz.mimar.modules.employee_details.data.model.EmployeeDetailsDataModel
import com.trianglz.mimar.modules.employee_details.data.retrofit.service.EmployeeDetailsService
import javax.inject.Inject

class EmployeeDetailsRemoteDataSourceImpl @Inject constructor(private val service: EmployeeDetailsService) :
    EmployeeDetailsRemoteDataSource {
    override suspend fun getEmployeeDetails(employeeId: Int): EmployeeDetailsDataModel {
        return service.getEmployeeDetails(employeeId).employee
    }

}
