package com.trianglz.mimar.modules.addresses.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.addresses.domain.repository.AddressRepository
import javax.inject.Inject

class DeleteAddressUseCase @Inject constructor(
    private val addressRepository: AddressRepository
) : BaseUseCase {
    suspend fun execute(addressId: Int) {
        addressRepository.deleteAddress(addressId)
    }
}