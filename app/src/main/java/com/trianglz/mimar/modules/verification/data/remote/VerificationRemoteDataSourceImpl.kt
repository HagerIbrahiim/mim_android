package com.trianglz.mimar.modules.verification.data.remote

import com.trianglz.mimar.modules.user.data.model.UserDataModel
import com.trianglz.mimar.modules.verification.data.retrofit.request.OTPRequestModel
import com.trianglz.mimar.modules.verification.data.retrofit.service.VerificationService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class VerificationRemoteDataSourceImpl @Inject constructor(private val service: VerificationService) :
    VerificationRemoteDataSource {
    override suspend fun verifyAccount(otpRequestModel: OTPRequestModel): UserDataModel {
       return service.verifyAccountAsync(otpRequestModel).customer
    }

    override suspend fun resendVerificationCode() {
        service.resendVerificationCodeAsync()
    }
}