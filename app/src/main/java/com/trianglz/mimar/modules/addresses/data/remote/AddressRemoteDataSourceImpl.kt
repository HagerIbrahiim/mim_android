package com.trianglz.mimar.modules.addresses.data.remote

import com.trianglz.mimar.common.data.retrofit.ApiQueries.Flags.Companion.FLAG_SUPPORTED
import com.trianglz.mimar.modules.addresses.data.model.AddressInfoDataModel
import com.trianglz.mimar.modules.addresses.data.model.CustomerAddressDataModel
import com.trianglz.mimar.modules.addresses.data.retrofit.request.AddressRequestModel
import com.trianglz.mimar.modules.addresses.data.retrofit.service.AddressApisService
import javax.inject.Inject

class AddressRemoteDataSourceImpl @Inject constructor(
    private val service: AddressApisService
) : AddressRemoteDataSource {
    override suspend fun createAddress(
        createAddressRequestModel: AddressRequestModel
    ): CustomerAddressDataModel {
        return service.createAddress(createAddressRequestModel).customerAddress
    }

    override suspend fun editAddressInfo(
        addressRequestModel: AddressRequestModel
    ): CustomerAddressDataModel {
        return service.editAddress(addressRequestModel.addressId ?: -1,addressRequestModel).customerAddress
    }

    override suspend fun getAddressList(page: Int, perPage: Int, filterByBranchIdInCart: Boolean?): List<CustomerAddressDataModel> {
        return service.getAddresses(
            page= page,
            items = perPage,
            supportedList = if (filterByBranchIdInCart == true) FLAG_SUPPORTED else null
        ).customerAddresses
    }

    override suspend fun changeDefaultAddress(addressId: Int): CustomerAddressDataModel {
        return service.setDefaultAddress(addressId).customerAddress

    }

    override suspend fun deleteAddress(addressId: Int) {
        service.deleteAddress(addressId)
    }

    override suspend fun fetchAddressInfoFromMap(lat: Double, long: Double): AddressInfoDataModel {
        return service.fetchDataFromMap(lat.toFloat(),long.toFloat()).addressData
    }


}