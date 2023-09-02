package com.trianglz.mimar.modules.branches.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import javax.inject.Inject

class GetBranchesListUseCase @Inject constructor(private val repo: BranchesRepository) :
    BaseUseCase {
    suspend fun execute(page: Int = 1,
                        perPage: Int = 10,
                        lat: Double,
                        lng: Double,
                        name: String? = null): List<BranchDomainModel> {
        return repo.getBranchesList(page, perPage, lat,lng,name)
    }
}