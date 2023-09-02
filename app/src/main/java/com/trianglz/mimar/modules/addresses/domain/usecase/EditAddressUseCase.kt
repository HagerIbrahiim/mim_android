package com.trianglz.mimar.modules.addresses.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel
import com.trianglz.mimar.modules.addresses.domain.model.EditAddAddressDomainModel
import com.trianglz.mimar.modules.addresses.domain.repository.AddressRepository
import javax.inject.Inject


class EditAddressUseCase @Inject constructor(
    private val addressFormRepository: AddressRepository
) : BaseUseCase {
    suspend fun execute(
        editAddAddressDomainModel: EditAddAddressDomainModel,
    ): CustomerAddressDomainModel {
        return addressFormRepository.editAddressInfo(
            editAddAddressDomainModel
        )
    }
}