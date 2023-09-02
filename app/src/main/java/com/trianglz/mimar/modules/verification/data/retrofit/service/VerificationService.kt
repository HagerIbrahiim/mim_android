package com.trianglz.mimar.modules.verification.data.retrofit.service

import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.user.data.retrofit.response.UserResponseModel
import com.trianglz.mimar.modules.verification.data.retrofit.request.OTPRequestModel
import retrofit2.http.Body
import retrofit2.http.POST

interface VerificationService {

    @POST(ApiPaths.VERIFY_PHONE)
    suspend fun verifyAccountAsync(@Body otpRequestModel: OTPRequestModel): UserResponseModel

    @POST(ApiPaths.RESEND_CODE)
    suspend fun resendVerificationCodeAsync(
        ): SuccessMessageResponse

}