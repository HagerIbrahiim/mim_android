package com.trianglz.mimar.modules.cart.data.repository

import com.trianglz.mimar.modules.cart.data.remote.data_source.CartRemoteDataSource
import com.trianglz.mimar.modules.cart.data.remote.mapper.toDomain
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.domain.model.UpdateCartDomainModel
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import com.trianglz.mimar.modules.services.data.mapper.toDomain
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val remote: CartRemoteDataSource) :
    CartRepository {

    private val modifiedServices =
        MutableSharedFlow<ServiceDomainModel>(0, Int.MAX_VALUE, BufferOverflow.SUSPEND)
    override suspend fun getCart(): CartDomainModel? {
        return remote.getCart()?.toDomain()
    }

    override suspend fun removeServiceFromCart(branchServiceId: Int) {
        val updatedItem = remote.removeServiceFromCart(
            branchServiceId
        )?.toDomain()
        updatedItem?.let {
            modifiedServices.emit(updatedItem)
        }
    }

    override suspend fun clearCart() {
        val services = remote.clearCart().map { it.toDomain() }
        services.forEach {
            modifiedServices.emit(it)
        }
    }

    override suspend fun updateCart(updateCartDomainModel: UpdateCartDomainModel): CartDomainModel? {
        return remote.updateCart(updateCartDomainModel)?.toDomain()
    }

    override suspend fun addServiceToCart(service: ServiceDomainModel) {
        val updatedItem = remote.addServiceToCart(
            service.id ?: -1, service.isAdded ?: false
        )?.toDomain()
        updatedItem?.let {
            modifiedServices.emit(updatedItem)
        }
    }
    
    override suspend fun getServicesUpdates(): SharedFlow<ServiceDomainModel> {
        return modifiedServices
    }

    override suspend fun updateServices(services: List<ServiceDomainModel>) {
        services.forEach {
            modifiedServices.emit(it)
        }
    }

    override suspend fun updateBranchServiceInCart(branchServiceId: Int, employeeId: Int?,isAnyone: Boolean?): CartDomainModel? {
//        val updatedItem = remote.updateBranchServiceInCart(
//            branchServiceId, employeeId
//        )?.toDomain()
//        updatedItem?.let {
//            modifiedServices.emit(updatedItem)
//        }
        return remote.updateBranchServiceInCart(branchServiceId, employeeId, isAnyone)?.toDomain()
    }

    override suspend fun connectGetCartUpdates(): Flow<CartDomainModel> {
        return remote.connectGetCartUpdates().map { it.toDomain() }
    }
    override suspend fun reconnectCartSocket(){
        remote.reconnectCartSocket()
    }
    override fun disconnectGetCartUpdates() {
        remote.disconnectGetCartUpdates()
    }
}
