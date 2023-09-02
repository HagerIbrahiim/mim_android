package com.trianglz.mimar.modules.branches.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.branches.domain.model.BranchDetailsDomainModel
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import javax.inject.Inject

class GetBranchDetailsUseCase @Inject constructor(private val repo: BranchesRepository) :
    BaseUseCase {
    suspend fun execute(branchId: Int): BranchDetailsDomainModel {
        return repo.getBranchDetails(branchId)
    }
}