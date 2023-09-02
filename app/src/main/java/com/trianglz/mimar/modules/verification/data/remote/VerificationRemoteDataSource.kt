package com.trianglz.mimar.modules.verification.data.remote

import com.trianglz.mimar.modules.user.data.model.UserDataModel
import com.trianglz.mimar.modules.verification.data.retrofit.request.OTPRequestModel

interface VerificationRemoteDataSource {

    suspend fun verifyAccount(otpRequestModel: OTPRequestModel): UserDataModel
    suspend fun resendVerificationCode()
}