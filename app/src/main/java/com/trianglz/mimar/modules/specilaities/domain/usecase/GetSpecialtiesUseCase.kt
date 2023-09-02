package com.trianglz.mimar.modules.specilaities.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel
import com.trianglz.mimar.modules.specilaities.domain.repository.SpecialtiesRepository
import javax.inject.Inject

class GetSpecialtiesUseCase @Inject constructor(private val repo: SpecialtiesRepository) :
    BaseUseCase {
    suspend fun execute(branchId: Int? = null): List<SpecialtiesDomainModel> {
        return repo.getSpecialties(branchId)
    }
}