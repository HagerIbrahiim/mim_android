package com.trianglz.mimar.modules.user.data.remote

import com.trianglz.mimar.modules.user.data.model.UserDataModel
import com.trianglz.mimar.modules.user.data.retrofit.request.LogoutRequestModel
import com.trianglz.mimar.modules.user.data.retrofit.request.UpdateUserRequestModel
import com.trianglz.mimar.modules.user.data.retrofit.service.UserApisService
import javax.inject.Inject

/**
 * Created by hassankhamis on 04,January,2023
 */
class UserRemoteDataSourceImpl @Inject constructor(
    private val service: UserApisService,
): UserRemoteDataSource {
    override suspend fun updateUser(updateUserRequestModel: UpdateUserRequestModel): UserDataModel {
        val response = service.updateUserAsync(
            updateUserRequestModel
        )
        return response.customer
    }

    override suspend fun clearUser(deviceId: String) {
        service.logoutAsync(
            LogoutRequestModel(deviceId)
        )
    }

    override suspend fun deleteUser() {
        service.deleteAccountAsync()
    }
}
