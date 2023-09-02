package com.trianglz.mimar.modules.forgot_password.domain.repository

import com.trianglz.mimar.modules.forgot_password.domain.model.ForgotPasswordDomainModel


interface ForgotPasswordRepo {

    suspend fun forgotPassword(forgotPasswordDomainModel: ForgotPasswordDomainModel)
}