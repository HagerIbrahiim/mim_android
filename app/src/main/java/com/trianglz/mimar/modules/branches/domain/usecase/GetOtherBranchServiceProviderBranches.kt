package com.trianglz.mimar.modules.branches.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetOtherBranchServiceProviderBranches @Inject constructor(private val repo: BranchesRepository) :
    BaseUseCase {
    suspend fun execute(branchId: Int): List<BranchDomainModel>  {
        return repo.getOtherBranchServiceProviderBranches(branchId)
    }
}