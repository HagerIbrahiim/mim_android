package com.trianglz.mimar.modules.sign_up.data.retrofit.service

import com.trianglz.mimar.modules.sign_up.data.retrofit.request.SignUpRequestModel
import retrofit2.http.POST
import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.user.data.model.UserDataModel
import com.trianglz.mimar.modules.user.data.retrofit.response.UserResponseModel
import retrofit2.http.Body

interface SignUpService {
    @POST(ApiPaths.CUSTOMERS)
    suspend fun signUpAsync(@Body signUpRequestModel: SignUpRequestModel): UserResponseModel
}