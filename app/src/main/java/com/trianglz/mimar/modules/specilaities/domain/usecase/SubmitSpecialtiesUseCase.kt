package com.trianglz.mimar.modules.specilaities.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel
import com.trianglz.mimar.modules.specilaities.domain.repository.SpecialtiesRepository
import javax.inject.Inject

class SubmitSpecialtiesUseCase @Inject constructor(private val repo: SpecialtiesRepository) :
    BaseUseCase {
    fun execute(specialties: List<SpecialtiesDomainModel>){
        repo.submitSpecialties(specialties)
    }
}