package com.trianglz.mimar.modules.user.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.user.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SetUserUseCase @Inject constructor(private val userRepository: UserRepository): BaseUseCase {
    suspend fun execute(user: UserDomainModel, isSetAuthToken: Boolean = true) {
        userRepository.setUser(user, isSetAuthToken)
    }
}