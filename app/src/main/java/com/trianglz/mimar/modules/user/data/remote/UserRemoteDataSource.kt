package com.trianglz.mimar.modules.user.data.remote

import com.trianglz.mimar.modules.user.data.model.UserDataModel
import com.trianglz.mimar.modules.user.data.retrofit.request.UpdateUserRequestModel

/**
 * Created by hassankhamis on 25,July,2022
 */
interface UserRemoteDataSource {
    suspend fun updateUser(updateUserRequestModel: UpdateUserRequestModel): UserDataModel
    suspend fun clearUser(deviceId: String)
    suspend fun deleteUser()
}
