package com.trianglz.mimar.modules.sign_in.data.remote

import com.trianglz.mimar.modules.sign_in.data.retrofit.request.SignInRequestModel
import com.trianglz.mimar.modules.sign_in.data.retrofit.request.SocialLoginRequest
import com.trianglz.mimar.modules.sign_in.data.retrofit.service.SignInService
import com.trianglz.mimar.modules.user.data.model.UserDataModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class SignInRemoteDataSourceImpl @Inject constructor(val service: SignInService) :
    SignInRemoteDataSource {
    override suspend fun signIn(signInRequestModel: SignInRequestModel) : UserDataModel {
        return service.signIn(signInRequestModel).customer
    }

    override suspend fun socialLogin(socialLoginRequest: SocialLoginRequest): UserDataModel {
        return  service.socialLoginAsync(socialLoginRequest).customer
    }
}