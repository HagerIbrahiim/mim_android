package com.trianglz.mimar.modules.addresses.data.retrofit.service

import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.common.data.retrofit.ApiQueries
import com.trianglz.mimar.modules.addresses.data.retrofit.request.AddressRequestModel
import com.trianglz.mimar.modules.addresses.data.retrofit.response.AddressInfoResponseModel
import com.trianglz.mimar.modules.addresses.data.retrofit.response.AddressResponseModel
import com.trianglz.mimar.modules.addresses.data.retrofit.response.AddressesListResponseModel
import retrofit2.http.*


interface AddressApisService {

    @POST(ApiPaths.CUSTOMER_ADDRESSES)
    suspend fun createAddress(@Body addressRequestModel: AddressRequestModel): AddressResponseModel

    @PATCH(ApiPaths.UPDATE_ADDRESS)
    suspend fun editAddress(@Path("id") id: Int,
                            @Body addressRequestModel: AddressRequestModel): AddressResponseModel

    @GET(ApiPaths.FETCH_DATA_MAP)
    suspend fun fetchDataFromMap(
        @Query(ApiQueries.LAT) lat: Float,
        @Query(ApiQueries.LNG) lng: Float): AddressInfoResponseModel


    @GET(ApiPaths.Customer_Addresses)
    suspend fun getAddresses(
        @Query(ApiQueries.PAGE) page: Int = 1,
        @Query(ApiQueries.ITEMS) items: Int = 10,
        @Query(ApiQueries.LIST) supportedList: String? = null,
        ): AddressesListResponseModel

    @POST(ApiPaths.SET_DEFAULT_ADDRESS)
    suspend fun setDefaultAddress(
        @Path("id") id: Int,
    ): AddressResponseModel

    @DELETE(ApiPaths.DELETE_ADDRESS)
    suspend fun deleteAddress(
        @Path("id") id: Int,
    ): SuccessMessageResponse



}