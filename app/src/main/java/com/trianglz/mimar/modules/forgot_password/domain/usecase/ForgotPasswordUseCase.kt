package com.trianglz.mimar.modules.forgot_password.domain.usecase

import com.trianglz.mimar.modules.forgot_password.domain.model.ForgotPasswordDomainModel
import com.trianglz.mimar.modules.forgot_password.domain.repository.ForgotPasswordRepo
import javax.inject.Inject


class ForgotPasswordUseCase @Inject constructor(private val repo: ForgotPasswordRepo) {

    suspend fun execute(forgotPasswordDomainModel: ForgotPasswordDomainModel) {
        repo.forgotPassword(forgotPasswordDomainModel)
    }
}