package com.trianglz.mimar.modules.cart.domain.usecase

import com.trianglz.mimar.modules.agg_user_cart.domain.repository.UserCartRepository
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import javax.inject.Inject

class ClearCartUseCase @Inject constructor(private val repo: UserCartRepository) {
    suspend fun execute() {
        return repo.clearCart()
    }
}
