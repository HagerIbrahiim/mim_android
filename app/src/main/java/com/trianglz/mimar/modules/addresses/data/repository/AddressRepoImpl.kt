package com.trianglz.mimar.modules.addresses.data.repository

import com.trianglz.mimar.modules.addresses.data.mapper.toDomain
import com.trianglz.mimar.modules.addresses.data.mapper.toRequest
import com.trianglz.mimar.modules.addresses.data.remote.AddressRemoteDataSource
import com.trianglz.mimar.modules.addresses.domain.model.AddressInfoDomainModel
import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel
import com.trianglz.mimar.modules.addresses.domain.model.EditAddAddressDomainModel
import com.trianglz.mimar.modules.addresses.domain.repository.AddressRepository
import javax.inject.Inject

class AddressRepoImpl @Inject constructor(
    private val addressRemoteDataSource: AddressRemoteDataSource
) : AddressRepository {
    override suspend fun createAddress(
        addEditAddAddressDomainModel: EditAddAddressDomainModel
    ): CustomerAddressDomainModel {

        return addressRemoteDataSource.createAddress(
            addEditAddAddressDomainModel.toRequest()
        ).toDomain()
    }

    override suspend fun editAddressInfo(
        editAddAddressDomainModel: EditAddAddressDomainModel,
    ): CustomerAddressDomainModel {
        return addressRemoteDataSource.editAddressInfo(
            editAddAddressDomainModel.toRequest()
        ).toDomain()

    }

    override suspend fun getAddressList(
        page: Int, perPage: Int,filterByBranchIdInCart: Boolean?
    ): List<CustomerAddressDomainModel> {
        return addressRemoteDataSource.getAddressList(page, perPage, filterByBranchIdInCart).map { it.toDomain() }
    }

    override suspend fun changeDefaultAddress(addressId: Int): CustomerAddressDomainModel {
        return addressRemoteDataSource.changeDefaultAddress(addressId).toDomain()
    }

    override suspend fun deleteAddress(addressId: Int) {
        addressRemoteDataSource.deleteAddress(addressId)
    }

    override suspend fun fetchAddressInfoFromMap(
        lat: Double,
        long: Double
    ): AddressInfoDomainModel {
       return addressRemoteDataSource.fetchAddressInfoFromMap(lat, long).toDomain()
    }

}