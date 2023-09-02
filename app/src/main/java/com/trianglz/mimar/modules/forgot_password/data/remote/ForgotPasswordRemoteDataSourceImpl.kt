package com.trianglz.mimar.modules.forgot_password.data.remote

import com.trianglz.mimar.modules.forgot_password.data.retrofit.request.ForgotPasswordRequestModel
import com.trianglz.mimar.modules.forgot_password.data.retrofit.service.ForgotPasswordService
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import javax.inject.Inject

@ViewModelScoped
class ForgotPasswordRemoteDataSourceImpl @Inject constructor(private val service: ForgotPasswordService) :
    ForgotPasswordRemoteDataSource {
    override suspend fun forgotPassword(forgotPasswordRequestModel: ForgotPasswordRequestModel) {
        service.forgotPasswordAsync(forgotPasswordRequestModel)
    }
}