package com.trianglz.mimar.modules.forgot_password.data.repository

import com.trianglz.mimar.modules.forgot_password.data.mapper.toRequest
import com.trianglz.mimar.modules.forgot_password.data.remote.ForgotPasswordRemoteDataSource
import com.trianglz.mimar.modules.forgot_password.domain.model.ForgotPasswordDomainModel
import com.trianglz.mimar.modules.forgot_password.domain.repository.ForgotPasswordRepo
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ForgotPasswordRepoImpl @Inject constructor(private val remoteDataSource: ForgotPasswordRemoteDataSource) :
    ForgotPasswordRepo {
    override suspend fun forgotPassword(forgotPasswordDomainModel: ForgotPasswordDomainModel) {
        remoteDataSource.forgotPassword(forgotPasswordDomainModel.toRequest())
    }
}