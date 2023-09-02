package com.trianglz.mimar.modules.branches.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import com.trianglz.mimar.modules.time.domain.model.TimeDomainModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetBranchAvailableSlotsUseCase @Inject constructor(private val repo: BranchesRepository) :
    BaseUseCase {
    suspend fun execute(branchId: Int, date: String): List<TimeDomainModel>  {
        return repo.getBranchAvailableSlots(branchId, date)
    }
}