package com.trianglz.mimar.modules.addresses.data.remote

import com.trianglz.mimar.modules.addresses.data.model.AddressInfoDataModel
import com.trianglz.mimar.modules.addresses.data.model.CustomerAddressDataModel
import com.trianglz.mimar.modules.addresses.data.retrofit.request.AddressRequestModel


interface AddressRemoteDataSource {
    suspend fun createAddress(
       createAddressRequestModel: AddressRequestModel
    ): CustomerAddressDataModel

    suspend fun editAddressInfo(
        addressRequestModel: AddressRequestModel
    ): CustomerAddressDataModel

    suspend fun getAddressList(
        page: Int, perPage: Int, filterByBranchIdInCart: Boolean?
    ): List<CustomerAddressDataModel>


    suspend fun changeDefaultAddress(addressId: Int) : CustomerAddressDataModel

    suspend fun deleteAddress(
        addressId: Int,
    )

    suspend fun fetchAddressInfoFromMap(
        lat: Double,
        long: Double
    ): AddressInfoDataModel
}