package com.trianglz.mimar.modules.employee_details.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.employee_details.domain.model.EmployeeDetailsDomainModel
import com.trianglz.mimar.modules.employee_details.domain.repository.EmployeeDetailsRepository
import javax.inject.Inject

class GetEmployeeDetailsUseCase @Inject constructor(private val repo: EmployeeDetailsRepository) :
    BaseUseCase {
    suspend fun execute(employeeId: Int): EmployeeDetailsDomainModel {
        return repo.getEmployeeDetails(employeeId)
    }
}