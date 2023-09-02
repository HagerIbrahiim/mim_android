package com.trianglz.mimar.modules.serviced_genders.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderDomainModel
import com.trianglz.mimar.modules.serviced_genders.domain.repository.ServicedGendersRepository
import javax.inject.Inject

class SubmitServicedGendersUseCase @Inject constructor(private val repo: ServicedGendersRepository) :
    BaseUseCase {
    fun execute(servicedGenders: List<ServicedGenderDomainModel>){
        repo.submitServicedGenders(servicedGenders)
    }
}