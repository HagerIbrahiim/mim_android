package com.trianglz.mimar.modules.verification.domain.usecase

import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.verification.domain.model.OTPDomainModel
import com.trianglz.mimar.modules.verification.domain.repository.VerificationRepo
import javax.inject.Inject


class VerifyAccountUseCase @Inject constructor(private val repo: VerificationRepo) {

    suspend fun execute(otpDomainModel: OTPDomainModel) : UserDomainModel {
        return repo.verifyAccount(otpDomainModel)
    }

}