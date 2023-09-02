package com.trianglz.mimar.modules.user.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.user.domain.model.UpdateUserDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.user.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateUserUseCase @Inject constructor(private val userRepository: UserRepository):
    BaseUseCase {
    suspend fun execute(updateUserDomainModel: UpdateUserDomainModel) : UserDomainModel {
        return userRepository.updateUser(updateUserDomainModel)
    }
}