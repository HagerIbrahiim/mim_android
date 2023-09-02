package com.trianglz.mimar.modules.addresses.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.addresses.domain.repository.AddressRepository
import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel
import javax.inject.Inject


class GetAddressesListUseCase @Inject constructor(private val addressRepo: AddressRepository) :
    BaseUseCase {
    suspend fun execute(page: Int = -1, perPage: Int = 20, filterByBranchIdInCart: Boolean?): List<CustomerAddressDomainModel> {
        return addressRepo.getAddressList(page, perPage, filterByBranchIdInCart)
    }
}