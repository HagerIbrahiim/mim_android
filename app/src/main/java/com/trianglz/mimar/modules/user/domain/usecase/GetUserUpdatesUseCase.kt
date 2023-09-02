package com.trianglz.mimar.modules.user.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUpdatesUseCase @Inject constructor(private val userRepository: UserRepository):
    BaseUseCase {
    fun execute(): Flow<UserDomainModel> {
        return userRepository.getUserUpdates()

    }
}