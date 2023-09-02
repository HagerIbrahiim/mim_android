package com.trianglz.mimar.modules.cart.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.agg_user_cart.domain.repository.UserCartRepository
import javax.inject.Inject

class RemoveServiceFromCartUseCase @Inject constructor(
    private val repo: UserCartRepository
    ) :
    BaseUseCase {
    suspend fun execute(branchServiceId: Int) {
        repo.removeServiceFromCart(branchServiceId)
    }
}