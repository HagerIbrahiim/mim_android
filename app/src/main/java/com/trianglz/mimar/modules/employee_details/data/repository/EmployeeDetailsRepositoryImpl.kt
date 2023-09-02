package com.trianglz.mimar.modules.employee_details.data.repository


import com.trianglz.mimar.modules.employee_details.data.data_source.EmployeeDetailsRemoteDataSource
import com.trianglz.mimar.modules.employee_details.data.mapper.toDomain
import com.trianglz.mimar.modules.employee_details.domain.model.EmployeeDetailsDomainModel
import com.trianglz.mimar.modules.employee_details.domain.repository.EmployeeDetailsRepository
import javax.inject.Inject

class EmployeeDetailsRepositoryImpl @Inject constructor(private val remote: EmployeeDetailsRemoteDataSource) :
    EmployeeDetailsRepository {
    override suspend fun getEmployeeDetails(employeeId: Int): EmployeeDetailsDomainModel {
        return remote.getEmployeeDetails(employeeId).toDomain()
    }

}
