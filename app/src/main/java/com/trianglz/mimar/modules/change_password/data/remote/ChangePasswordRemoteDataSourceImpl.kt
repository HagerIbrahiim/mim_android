package com.trianglz.mimar.modules.change_password.data.remote

import com.trianglz.mimar.modules.change_password.data.retrofit.request.ChangePasswordRequestModel
import com.trianglz.mimar.modules.change_password.data.retrofit.service.ChangePasswordService
import com.trianglz.mimar.modules.user.data.model.UserDataModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import javax.inject.Inject

@ViewModelScoped
class ChangePasswordRemoteDataSourceImpl @Inject constructor(val service: ChangePasswordService) :
    ChangePasswordRemoteDataSource {
    override suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel): UserDataModel {

       return service.changePassword(changePasswordRequestModel).customer

    }
}