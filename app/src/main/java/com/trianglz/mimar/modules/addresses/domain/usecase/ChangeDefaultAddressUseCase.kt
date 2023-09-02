package com.trianglz.mimar.modules.addresses.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel
import com.trianglz.mimar.modules.addresses.domain.repository.AddressRepository
import javax.inject.Inject

class ChangeDefaultAddressUseCase @Inject constructor(private val addressRepo: AddressRepository) :
    BaseUseCase {
    suspend fun execute(addressId: Int) : CustomerAddressDomainModel {
       return addressRepo.changeDefaultAddress(addressId)
    }
}