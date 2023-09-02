package com.trianglz.mimar.modules.sign_in.data.repository

import com.trianglz.mimar.modules.sign_in.data.mapper.toRequest
import com.trianglz.mimar.modules.sign_in.data.remote.SignInRemoteDataSource
import com.trianglz.mimar.modules.sign_in.domain.model.SignInDomainModel
import com.trianglz.mimar.modules.sign_in.domain.model.SocialLoginDomainModel
import com.trianglz.mimar.modules.sign_in.domain.repository.SignInRepo
import com.trianglz.mimar.modules.user.data.mapper.toDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import javax.inject.Inject

class SignInRepoImpl @Inject constructor(private val remote: SignInRemoteDataSource) : SignInRepo {

    override suspend fun signIn(signInDomainModel: SignInDomainModel): UserDomainModel {
        return remote.signIn(signInDomainModel.toRequest()).toDomainModel()
    }

    override suspend fun socialLogin(socialLoginDomainModel: SocialLoginDomainModel): UserDomainModel {
        return remote.socialLogin(socialLoginDomainModel.toRequest()).toDomainModel()
    }
}