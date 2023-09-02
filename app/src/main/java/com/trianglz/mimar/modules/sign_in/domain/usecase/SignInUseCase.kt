package com.trianglz.mimar.modules.sign_in.domain.usecase

import com.trianglz.mimar.modules.sign_in.domain.model.SignInDomainModel
import com.trianglz.mimar.modules.sign_in.domain.repository.SignInRepo
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import javax.inject.Inject


class SignInUseCase @Inject constructor(private val repo: SignInRepo) {
    suspend fun execute(signInDomainModel: SignInDomainModel): UserDomainModel {
        return repo.signIn(signInDomainModel)
    }
}