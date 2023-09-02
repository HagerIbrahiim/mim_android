package com.trianglz.mimar.modules.verification.data.repository

import com.trianglz.mimar.modules.user.data.mapper.toDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.verification.data.mapper.toRequest
import com.trianglz.mimar.modules.verification.data.remote.VerificationRemoteDataSource
import com.trianglz.mimar.modules.verification.domain.model.OTPDomainModel
import com.trianglz.mimar.modules.verification.domain.repository.VerificationRepo
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class VerificationRepoImpl @Inject constructor(private val remoteDataSource: VerificationRemoteDataSource) :
    VerificationRepo {
    override suspend fun verifyAccount(otpDomainModel: OTPDomainModel): UserDomainModel {
        return remoteDataSource.verifyAccount(otpDomainModel.toRequest()).toDomainModel()
    }

    override suspend fun resendVerificationCode() {
        remoteDataSource.resendVerificationCode()
    }
}