package com.trianglz.mimar.modules.user.domain.usecase

import com.trianglz.core.domain.exceptions.CustomException
import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.user.domain.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUseCase @Inject constructor(private val userRepository: UserRepository): BaseUseCase {
    suspend fun execute(): UserDomainModel {
        return try {
            userRepository.getUser()
        } catch (e : Exception) {
            Timber.tag("GetUserUserCase").d(e)
            throw CustomException.AuthorizationException
        }
    }
}
