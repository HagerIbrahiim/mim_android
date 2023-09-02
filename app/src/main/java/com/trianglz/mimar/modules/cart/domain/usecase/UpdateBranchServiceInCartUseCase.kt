package com.trianglz.mimar.modules.cart.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import javax.inject.Inject

class UpdateBranchServiceInCartUseCase @Inject constructor(
    private val repo: CartRepository,
) : BaseUseCase {
    suspend fun execute(
        branchServiceId: Int,
        employeeId: Int? = null,
        isAnyone: Boolean? = null
    ): CartDomainModel? {
        return repo.updateBranchServiceInCart(branchServiceId, employeeId, isAnyone)
    }
}