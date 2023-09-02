package com.trianglz.mimar.modules.addresses.domain.repository

import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel
import com.trianglz.mimar.modules.addresses.domain.model.AddressInfoDomainModel
import com.trianglz.mimar.modules.addresses.domain.model.EditAddAddressDomainModel

interface AddressRepository {

    suspend fun createAddress(
        addEditAddAddressDomainModel: EditAddAddressDomainModel
    ): CustomerAddressDomainModel

    suspend fun editAddressInfo(
        editAddAddressDomainModel: EditAddAddressDomainModel,
    ): CustomerAddressDomainModel

    suspend fun getAddressList(
        page: Int, perPage: Int,filterByBranchIdInCart: Boolean?
    ): List<CustomerAddressDomainModel>


    suspend fun changeDefaultAddress(addressId: Int) : CustomerAddressDomainModel

    suspend fun deleteAddress(
        addressId: Int,
    )
    suspend fun fetchAddressInfoFromMap(
        lat: Double,
        long: Double
    ): AddressInfoDomainModel
}