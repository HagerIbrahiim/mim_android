package com.trianglz.mimar.modules.verification.domain.repository

import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.verification.domain.model.OTPDomainModel


interface VerificationRepo {

    suspend fun verifyAccount(otpDomainModel: OTPDomainModel): UserDomainModel
    suspend fun resendVerificationCode()
}