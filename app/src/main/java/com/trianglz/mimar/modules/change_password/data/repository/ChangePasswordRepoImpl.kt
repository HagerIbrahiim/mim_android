package com.trianglz.mimar.modules.change_password.data.repository

import com.trianglz.mimar.modules.change_password.data.mapper.toRequest
import com.trianglz.mimar.modules.change_password.data.remote.ChangePasswordRemoteDataSource
import com.trianglz.mimar.modules.change_password.domain.model.ChangePasswordDomainModel
import com.trianglz.mimar.modules.change_password.domain.repository.ChangePasswordRepo
import com.trianglz.mimar.modules.user.data.mapper.toDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ChangePasswordRepoImpl @Inject constructor(private val remote: ChangePasswordRemoteDataSource) :
    ChangePasswordRepo {

    override suspend fun changePassword(changePasswordDomainModel: ChangePasswordDomainModel): UserDomainModel {
        return remote.changePassword(changePasswordDomainModel.toRequest()).toDomainModel()
    }
}