package com.trianglz.mimar.modules.employee_details.data.data_source

import com.trianglz.mimar.modules.employee_details.data.model.EmployeeDetailsDataModel

interface EmployeeDetailsRemoteDataSource {
    suspend fun getEmployeeDetails(employeeId: Int): EmployeeDetailsDataModel
}