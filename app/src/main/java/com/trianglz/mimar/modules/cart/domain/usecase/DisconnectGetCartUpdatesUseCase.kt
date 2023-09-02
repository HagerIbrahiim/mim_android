package com.trianglz.mimar.modules.cart.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import javax.inject.Inject

class DisconnectGetCartUpdatesUseCase @Inject constructor(private val repo: CartRepository) :
    BaseUseCase {

    fun execute() {
        repo.disconnectGetCartUpdates()
    }

}