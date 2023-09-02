package com.trianglz.mimar.modules.cart.data.remote.data_source

import com.trianglz.mimar.modules.cart.data.remote.model.CartDataModel
import com.trianglz.mimar.modules.cart.domain.model.UpdateCartDomainModel
import com.trianglz.mimar.modules.services.data.model.ServiceDataModel
import kotlinx.coroutines.flow.Flow

interface CartRemoteDataSource {
    suspend fun getCart(): CartDataModel?
    suspend fun updateCart(updateCartDomainModel: UpdateCartDomainModel): CartDataModel?
    suspend fun removeServiceFromCart(branchServiceId: Int): ServiceDataModel?
    suspend fun clearCart(): List<ServiceDataModel>
    suspend fun addServiceToCart(id: Int, isAdded: Boolean): ServiceDataModel?
    suspend fun updateBranchServiceInCart(branchServiceId: Int, employeeId: Int?,isAnyone: Boolean?): CartDataModel?
    suspend fun connectGetCartUpdates(): Flow<CartDataModel>
    suspend fun reconnectCartSocket()
    fun disconnectGetCartUpdates()

}
