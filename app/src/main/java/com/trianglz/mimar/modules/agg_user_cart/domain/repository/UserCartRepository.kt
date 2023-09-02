package com.trianglz.mimar.modules.agg_user_cart.domain.repository

import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.domain.model.UpdateCartDomainModel
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel

interface UserCartRepository {
    suspend fun getUser(): UserDomainModel
    suspend fun updateUser()
    suspend fun updateCartInUser(cartDomainModel: CartDomainModel)
    suspend fun updateCart(updateCartDomainModel: UpdateCartDomainModel): CartDomainModel?
    suspend fun removeServiceFromCart(branchServiceId: Int)
    suspend fun clearCart()
    suspend fun addServiceToCart(service: ServiceDomainModel)

}