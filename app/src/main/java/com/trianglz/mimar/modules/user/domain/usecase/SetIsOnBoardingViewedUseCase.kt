package com.trianglz.mimar.modules.user.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.user.domain.repository.UserRepository
import javax.inject.Inject

class SetIsOnBoardingViewedUseCase @Inject constructor(private val userRepository: UserRepository):
    BaseUseCase {
    suspend fun execute(isnOBoardingViewed: Boolean) {
        userRepository.setOnBoardingViewed(isnOBoardingViewed)
    }
}