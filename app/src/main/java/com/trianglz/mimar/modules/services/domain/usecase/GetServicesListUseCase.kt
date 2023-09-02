package com.trianglz.mimar.modules.services.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.services.domain.repository.ServicesRepository
import javax.inject.Inject

class GetServicesListUseCase @Inject constructor(private val repo: ServicesRepository) :
    BaseUseCase {

    suspend fun execute(
        page: Int = 1,
        perPage: Int = 10,
        searchString: String? = null,
        specialityId: Int? = null,
        branchId: Int? = null,
        offeredLocation: String? = null
    ): List<ServiceDomainModel> {
        return repo.getServicesList(
            page = page,
            perPage = perPage,
            searchString = searchString,
            specialityId = specialityId,
            branchId = branchId,
            offeredLocation = offeredLocation
        )
    }
}