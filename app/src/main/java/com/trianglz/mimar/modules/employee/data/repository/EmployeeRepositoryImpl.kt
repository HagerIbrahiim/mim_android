package com.trianglz.mimar.modules.employee.data.repository

import com.trianglz.mimar.modules.employee.data.mapper.toDomain
import com.trianglz.mimar.modules.employee.data.remote.EmployeeRemoteDataSource
import com.trianglz.mimar.modules.employee.domain.model.EmployeeDomainModel
import com.trianglz.mimar.modules.employee.domain.repository.EmployeeRepository
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(private val remoteDataSource: EmployeeRemoteDataSource) :
    EmployeeRepository {
    override suspend fun getEmployeeList(
        page: Int?,
        perPage: Int?,
        branchId: Int?,
        cartBranchServiceId: Int?,
        date: String?,
        offeredLocation: String?,
    ): List<EmployeeDomainModel> {
        return remoteDataSource.getEmployeeList(
            page,
            perPage,
            branchId,
            cartBranchServiceId,
            date,
            offeredLocation
        ).map { it.toDomain() }
    }


}
