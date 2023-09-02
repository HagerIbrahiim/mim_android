package com.trianglz.mimar.modules.employee.domain.usecase

import com.trianglz.mimar.modules.employee.domain.model.EmployeeDomainModel
import com.trianglz.mimar.modules.employee.domain.repository.EmployeeRepository
import javax.inject.Inject

class GetAllEmployeesUseCase @Inject constructor(private val repo: EmployeeRepository) {

    suspend fun execute(
        page: Int? = null,
        perPage: Int? = null,
        branchId: Int? = null,
        cartBranchServiceId: Int? = null,
        date: String? = null,
        offeredLocation: String? = null,
    ): List<EmployeeDomainModel> {
        return repo.getEmployeeList(page, perPage, branchId, cartBranchServiceId, date, offeredLocation)
    }
}