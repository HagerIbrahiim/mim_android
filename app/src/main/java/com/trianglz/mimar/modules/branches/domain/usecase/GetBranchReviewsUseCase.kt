package com.trianglz.mimar.modules.branches.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.branches.domain.model.BranchReviewDomainModel
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import javax.inject.Inject

class GetBranchReviewsUseCase @Inject constructor(private val repo: BranchesRepository) :
    BaseUseCase {
    suspend fun execute(page: Int, perPage: Int,branchId: Int): List<BranchReviewDomainModel> {
        return repo.getBranchReviews(page, perPage, branchId)
    }
}