package com.trianglz.mimar.modules.cart.domain.usecase

import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import javax.inject.Inject

class GetCartUseCase @Inject constructor(private val repo: CartRepository) {
    suspend fun execute(): CartDomainModel? {
        return repo.getCart()
    }
}
