package com.trianglz.mimar.modules.employee_details.domain.repository

import com.trianglz.mimar.modules.employee_details.domain.model.EmployeeDetailsDomainModel

interface EmployeeDetailsRepository {
    suspend fun getEmployeeDetails(employeeId: Int): EmployeeDetailsDomainModel
}