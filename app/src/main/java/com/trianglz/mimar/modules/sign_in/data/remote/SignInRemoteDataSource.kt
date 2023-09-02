package com.trianglz.mimar.modules.sign_in.data.remote

import com.trianglz.mimar.modules.sign_in.data.retrofit.request.SignInRequestModel
import com.trianglz.mimar.modules.sign_in.data.retrofit.request.SocialLoginRequest
import com.trianglz.mimar.modules.user.data.model.UserDataModel

interface SignInRemoteDataSource {
    suspend fun signIn(signInRequestModel: SignInRequestModel): UserDataModel
    suspend fun socialLogin(socialLoginRequest: SocialLoginRequest): UserDataModel

}