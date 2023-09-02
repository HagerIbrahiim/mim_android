package com.trianglz.mimar.modules.cart.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.agg_user_cart.domain.repository.UserCartRepository
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import javax.inject.Inject

class AddServiceToCartUseCase @Inject constructor(
    private val repo: UserCartRepository,
) : BaseUseCase {
    suspend fun execute(service: ServiceDomainModel) {
        repo.addServiceToCart(service)
    }
}