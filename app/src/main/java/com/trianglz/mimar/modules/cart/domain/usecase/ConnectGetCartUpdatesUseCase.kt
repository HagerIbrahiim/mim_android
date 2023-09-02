package com.trianglz.mimar.modules.cart.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.domain.repository.CartRepository
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConnectGetCartUpdatesUseCase @Inject constructor(private val repo: CartRepository) :
    BaseUseCase {

    suspend fun execute(): Flow<CartDomainModel> {
        return repo.connectGetCartUpdates()
    }

}