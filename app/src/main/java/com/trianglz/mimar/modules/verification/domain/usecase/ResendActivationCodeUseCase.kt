package com.trianglz.mimar.modules.verification.domain.usecase

import com.trianglz.mimar.modules.verification.domain.repository.VerificationRepo
import javax.inject.Inject

class ResendActivationCodeUseCase @Inject constructor(private val repo: VerificationRepo) {

    suspend fun execute() {
        return repo.resendVerificationCode()
    }

}