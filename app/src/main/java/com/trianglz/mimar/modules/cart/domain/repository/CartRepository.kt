package com.trianglz.mimar.modules.cart.domain.repository

import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.domain.model.UpdateCartDomainModel
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface CartRepository {
    suspend fun getCart(): CartDomainModel?
    suspend fun updateCart(updateCartDomainModel: UpdateCartDomainModel): CartDomainModel?
    suspend fun removeServiceFromCart(branchServiceId: Int)
    suspend fun clearCart()
    suspend fun addServiceToCart(service: ServiceDomainModel)
    suspend fun getServicesUpdates(): SharedFlow<ServiceDomainModel>
    suspend fun updateServices(services: List<ServiceDomainModel>)
    suspend fun updateBranchServiceInCart(branchServiceId: Int, employeeId: Int?,isAnyone: Boolean?): CartDomainModel?
    suspend fun connectGetCartUpdates(): Flow<CartDomainModel>
    suspend fun reconnectCartSocket()
    fun disconnectGetCartUpdates()

}
