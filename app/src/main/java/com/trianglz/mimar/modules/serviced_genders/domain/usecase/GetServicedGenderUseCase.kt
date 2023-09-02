package com.trianglz.mimar.modules.serviced_genders.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderDomainModel
import com.trianglz.mimar.modules.serviced_genders.domain.repository.ServicedGendersRepository
import javax.inject.Inject

class GetServicedGenderUseCase @Inject constructor(private val repo: ServicedGendersRepository) :
    BaseUseCase {
    suspend fun execute(): List<ServicedGenderDomainModel> {
        return repo.getServicedGender()
    }
}