package com.trianglz.mimar.modules.change_password.data.remote

import com.trianglz.mimar.modules.change_password.data.retrofit.request.ChangePasswordRequestModel
import com.trianglz.mimar.modules.user.data.model.UserDataModel

interface ChangePasswordRemoteDataSource {
    suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel): UserDataModel
}