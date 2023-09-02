package com.trianglz.mimar.modules.sign_in.domain.repository

import com.trianglz.mimar.modules.sign_in.domain.model.SignInDomainModel
import com.trianglz.mimar.modules.sign_in.domain.model.SocialLoginDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel

interface SignInRepo {
    suspend fun signIn(signInDomainModel: SignInDomainModel): UserDomainModel
    suspend fun socialLogin(socialLoginDomainModel: SocialLoginDomainModel):UserDomainModel

}