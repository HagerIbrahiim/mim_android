package com.trianglz.mimar.modules.branches.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import javax.inject.Inject

class GetFavoriteBranchesUseCase @Inject constructor(private val repo: BranchesRepository) :
    BaseUseCase {
    suspend fun execute(
        page: Int = 1,
        perPage: Int = 10,
        categoryId: Int? = null,
    ): List<BranchDomainModel> {
        return repo.getFavoriteBranches(
            page = page,
            perPage = perPage,
            categoryId = categoryId,

        )
    }
}