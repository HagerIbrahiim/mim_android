package com.trianglz.mimar.modules.addresses.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.addresses.domain.model.AddressInfoDomainModel
import com.trianglz.mimar.modules.addresses.domain.repository.AddressRepository
import javax.inject.Inject

class FetchAddressInfoFromMap @Inject constructor(private val addressRepository: AddressRepository) :
    BaseUseCase {
    suspend fun execute(
        lat: Double,
        long: Double
    ): AddressInfoDomainModel {
        return addressRepository.fetchAddressInfoFromMap(lat, long)
    }
}