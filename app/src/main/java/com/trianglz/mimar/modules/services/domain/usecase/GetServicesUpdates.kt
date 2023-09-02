package com.trianglz.mimar.modules.services.domain.usecase

import com.trianglz.core.domain.usecase.BaseUpdatesUseCase
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class GetServicesUpdates @Inject constructor(private val repo: CartRepository) :
    BaseUpdatesUseCase<ServiceDomainModel> {
    override suspend fun execute(): SharedFlow<ServiceDomainModel> {
        return repo.getServicesUpdates()
    }
}