package com.trianglz.mimar.modules.forgot_password.data.retrofit.service

import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.forgot_password.data.retrofit.request.ForgotPasswordRequestModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotPasswordService {

    @POST(ApiPaths.ForgotPassword)
    suspend fun forgotPasswordAsync(@Body forgotPasswordRequestModel: ForgotPasswordRequestModel): SuccessMessageResponse

}