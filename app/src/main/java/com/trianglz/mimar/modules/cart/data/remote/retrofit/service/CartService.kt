package com.trianglz.mimar.modules.cart.data.remote.retrofit.service

import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.cart.data.remote.retrofit.request.UpdateCartRequest
import com.trianglz.mimar.modules.cart.data.remote.retrofit.response.CartResponseModel
import com.trianglz.mimar.modules.cart.data.remote.retrofit.response.ClearCartResponseModel
import com.trianglz.mimar.modules.services.data.retrofit.request.AddServiceToCartRequest
import com.trianglz.mimar.modules.services.data.retrofit.request.UpdateBranchServiceInCartRequest
import com.trianglz.mimar.modules.services.data.retrofit.response.UpdatedServiceResponseModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CartService {
    @GET(ApiPaths.MY_CART)
    suspend fun getCartAsync(): CartResponseModel
    @DELETE(ApiPaths.CART_BRANCH_SERVICES_OPERATION)
    suspend fun removeServiceFromCartAsync(
        @Path(ApiPaths.ID) branchServiceId: Int
    ): UpdatedServiceResponseModel
    @POST(ApiPaths.CLEAR_CART)
    suspend fun clearCartAsync(): ClearCartResponseModel

    @PATCH(ApiPaths.UPDATE_CART)
    suspend fun updateCartAsync(
        @Body updateCartRequest: UpdateCartRequest
    ): CartResponseModel

    @POST(ApiPaths.CART_BRANCH_SERVICES)
    suspend fun addServiceToCartAsync(
        @Body addServiceToCartRequest: AddServiceToCartRequest
    ): UpdatedServiceResponseModel

    @PATCH(ApiPaths.CART_BRANCH_SERVICES_OPERATION)
    suspend fun updateBranchServiceInCartAsync(
        @Path(ApiPaths.ID) branchServiceId: Int,
        @Body updateBranchServiceInCartRequest: UpdateBranchServiceInCartRequest,
    ): CartResponseModel
}
