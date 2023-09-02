package com.trianglz.mimar.modules.sign_in.data.retrofit.service

import com.trianglz.mimar.modules.sign_in.data.retrofit.request.SignInRequestModel
import com.trianglz.mimar.modules.user.data.retrofit.response.UserResponseModel
import retrofit2.http.Body
import retrofit2.http.POST
import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.sign_in.data.retrofit.request.SocialLoginRequest

interface SignInService {

    @POST(ApiPaths.SESSIONS)
    suspend fun signIn(@Body signInRequestModel: SignInRequestModel): UserResponseModel

    @POST(ApiPaths.SOCIAL_LOGIN)
    suspend fun socialLoginAsync(@Body socialLoginRequest: SocialLoginRequest): UserResponseModel

}