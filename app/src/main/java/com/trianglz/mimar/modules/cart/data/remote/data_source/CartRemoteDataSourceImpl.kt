package com.trianglz.mimar.modules.cart.data.remote.data_source

import com.google.gson.JsonObject
import com.trianglz.mimar.modules.cart.data.remote.model.CartDataModel
import com.trianglz.mimar.modules.cart.data.remote.mapper.toRequestModel
import com.trianglz.mimar.modules.cart.data.remote.retrofit.response.CartResponseModel
import com.trianglz.mimar.modules.cart.data.remote.retrofit.service.CartService
import com.trianglz.mimar.modules.cart.domain.model.UpdateCartDomainModel
import com.trianglz.mimar.modules.cart.di.qualifiers.CartSocketServiceQualifier
import com.trianglz.mimar.modules.services.data.model.ServiceDataModel
import com.trianglz.mimar.modules.services.data.retrofit.request.AddServiceToCartRequest
import com.trianglz.mimar.modules.services.data.retrofit.request.UpdateBranchServiceInCartRequest
import com.trianglz.mimar.modules.socket.data.remote.service.SocketService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRemoteDataSourceImpl @Inject constructor(
    private val service: CartService,
    @CartSocketServiceQualifier private val socketService: SocketService<CartDataModel>
) :
    CartRemoteDataSource {
    override suspend fun getCart(): CartDataModel? {
        return service.getCartAsync().cart
    }

    override suspend fun removeServiceFromCart(branchServiceId: Int): ServiceDataModel? {
        return service.removeServiceFromCartAsync(branchServiceId).service
    }

    override suspend fun clearCart(): List<ServiceDataModel> {
        return service.clearCartAsync().branchServices ?: emptyList()
    }

    override suspend fun updateCart(updateCartDomainModel: UpdateCartDomainModel): CartDataModel? {
        return service.updateCartAsync(updateCartDomainModel.toRequestModel()).cart
    }

    override suspend fun addServiceToCart(id: Int, isAdded: Boolean): ServiceDataModel? {
        return service.addServiceToCartAsync(AddServiceToCartRequest(id)).service
    }

    override suspend fun updateBranchServiceInCart(
        branchServiceId: Int,
        employeeId: Int?,
        isAnyone: Boolean?,
    ): CartDataModel? {
        return service.updateBranchServiceInCartAsync(
            branchServiceId,
            UpdateBranchServiceInCartRequest(employeeId,isAnyone),
        ).cart
    }

    override suspend fun connectGetCartUpdates(): Flow<CartDataModel> {
        return socketService.connect(emptyMap(), onConnectSuccessfully = {
            socketService.sendMessageToServer(getCartSubscriptionCommand())
        }, jsonToModel = {
            CartResponseModel.create(it).cart
        })
    }

    override suspend fun reconnectCartSocket() {
        socketService.reconnect()
    }

    private fun getCartSubscriptionCommand(): String {
        val json = JsonObject()
        json.addProperty("command", "subscribe")
        json.addProperty("identifier", "{\"channel\":\"CartChannel\"}")
        return json.toString()
    }

    override fun disconnectGetCartUpdates() {
        socketService.disconnect()
    }
}
