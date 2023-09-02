package com.trianglz.mimar.modules.cart.domain.usecase

import com.trianglz.mimar.modules.agg_user_cart.domain.repository.UserCartRepository
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.domain.model.UpdateCartDomainModel
import javax.inject.Inject

class UpdateCartUseCase @Inject constructor(private val repo: UserCartRepository) {
    suspend fun execute(updateCartDomainModel: UpdateCartDomainModel): CartDomainModel? {
        return repo.updateCart(updateCartDomainModel)
    }
}
