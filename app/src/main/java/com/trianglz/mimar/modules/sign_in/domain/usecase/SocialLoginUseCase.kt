package com.trianglz.mimar.modules.sign_in.domain.usecase

import com.trianglz.mimar.modules.sign_in.domain.model.SocialLoginDomainModel
import com.trianglz.mimar.modules.sign_in.domain.repository.SignInRepo
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import javax.inject.Inject


class SocialLoginUseCase @Inject constructor(private val repo: SignInRepo) {
    suspend fun execute(socialLoginDomainModel: SocialLoginDomainModel): UserDomainModel {
        return repo.socialLogin(socialLoginDomainModel)
    }
}