package com.trianglz.mimar.modules.cart.domain.usecase

import com.trianglz.mimar.modules.agg_user_cart.domain.repository.UserCartRepository
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartInUserUseCase @Inject constructor(private val repo: UserCartRepository) {
    suspend fun execute(cartDomainModel: CartDomainModel) {
        repo.updateCartInUser(cartDomainModel)
    }
}
