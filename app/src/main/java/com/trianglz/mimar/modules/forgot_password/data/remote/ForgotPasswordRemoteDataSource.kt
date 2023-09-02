package com.trianglz.mimar.modules.forgot_password.data.remote

import com.trianglz.mimar.modules.forgot_password.data.retrofit.request.ForgotPasswordRequestModel

interface ForgotPasswordRemoteDataSource {

    suspend fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel)
}