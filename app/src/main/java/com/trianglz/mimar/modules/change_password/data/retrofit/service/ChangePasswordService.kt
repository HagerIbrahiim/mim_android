package com.trianglz.mimar.modules.change_password.data.retrofit.service

import com.trianglz.core.data.network.models.SuccessMessageResponse
import com.trianglz.mimar.common.data.retrofit.ApiPaths
import com.trianglz.mimar.modules.change_password.data.retrofit.request.ChangePasswordRequestModel
import com.trianglz.mimar.modules.sign_in.data.retrofit.request.SignInRequestModel
import com.trianglz.mimar.modules.user.data.model.UserDataModel
import com.trianglz.mimar.modules.user.data.retrofit.response.UserResponseModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ChangePasswordService {

    @POST(ApiPaths.ChangePassword)
    suspend fun changePassword(@Body changePasswordRequestModel: ChangePasswordRequestModel)
    : UserResponseModel

}