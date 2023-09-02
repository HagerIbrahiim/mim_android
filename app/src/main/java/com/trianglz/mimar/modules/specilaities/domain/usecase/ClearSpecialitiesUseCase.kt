package com.trianglz.mimar.modules.specilaities.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.specilaities.domain.repository.SpecialtiesRepository
import javax.inject.Inject

class ClearSpecialitiesUseCase @Inject constructor(private val repo: SpecialtiesRepository) :
    BaseUseCase {
    fun execute(){
        repo.clearSpecialities()
    }
}