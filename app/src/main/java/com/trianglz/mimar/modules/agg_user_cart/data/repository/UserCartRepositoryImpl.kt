package com.trianglz.mimar.modules.agg_user_cart.data.repository

import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.agg_user_cart.domain.repository.UserCartRepository
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.domain.model.UpdateCartDomainModel
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.user.domain.model.UpdateUserDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.user.domain.repository.UserRepository
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import javax.inject.Inject

class UserCartRepositoryImpl @Inject constructor(private val userModeHandler: UserModeHandler, private val userRepository: UserRepository, private val cartRepository: CartRepository): UserCartRepository {
    override suspend fun getUser(): UserDomainModel {
        return userRepository.getUser()
    }
    override suspend fun updateUser() {
        userRepository.updateUser(UpdateUserDomainModel())
    }
    override suspend fun updateCartInUser(cartDomainModel: CartDomainModel) {
//        userRepository.updateUser(UpdateUserDomainModel())
        val user = userRepository.getUser()
        userRepository.setUser(user.copy(cart = cartDomainModel), false)
    }

    override suspend fun updateCart(updateCartDomainModel: UpdateCartDomainModel): CartDomainModel? {
        val cart = cartRepository.updateCart(updateCartDomainModel)
        //TODO check only when appointment location changed
//        cart?.let { updateUser(it) }
        return cart
    }

    override suspend fun removeServiceFromCart(branchServiceId: Int) {
        if (!userModeHandler.isGuest()) {
            cartRepository.removeServiceFromCart(branchServiceId)
            updateUser()
        }
    }

    override suspend fun clearCart() {
        cartRepository.clearCart()
        updateUser()
    }

    override suspend fun addServiceToCart(service: ServiceDomainModel) {

        if (!userModeHandler.isGuest()) {
            val cart = getUser().cart
            if (cart == null
                || cart.cartBranchServicesCount == 0
                || (cart.branchId == service.branch?.uniqueId && (cart.appointmentLocation == service.offeredLocation || service.offeredLocation == OfferedLocationType.Both(StringWrapper()).key))
            ) {
                cartRepository.addServiceToCart(service)
                updateUser()
            } else {
                userModeHandler.notifyClearCart(service.uniqueId,  cart.branch?.name ?: cart.branchName ?: "")
            }
        }
    }

}