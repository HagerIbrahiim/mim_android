package com.trianglz.mimar.modules.user.data.retrofit.service

import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.user.data.retrofit.request.LogoutRequestModel
import com.trianglz.mimar.modules.user.data.retrofit.request.UpdateUserRequestModel
import com.trianglz.mimar.modules.user.data.retrofit.response.UserResponseModel
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.HTTP
import retrofit2.http.PATCH

interface UserApisService {
    @PATCH(ApiPaths.UPDATE_PROFILE)
    suspend fun updateUserAsync(
        @Body updateUserRequestModel: UpdateUserRequestModel
    ): UserResponseModel

    /**
     * we use this instead of @DELETE(ApiPaths.SESSIONS) because it doesn't support body in request
     */
    @HTTP(method = "DELETE", path = ApiPaths.SESSIONS, hasBody = true)
    suspend fun logoutAsync(
        @Body logoutRequestModel: LogoutRequestModel
    ): SuccessMessageResponse

    @HTTP(method = "DELETE", path = ApiPaths.DELETE_ACCOUNT, hasBody = true)
    suspend fun deleteAccountAsync(): SuccessMessageResponse

}
