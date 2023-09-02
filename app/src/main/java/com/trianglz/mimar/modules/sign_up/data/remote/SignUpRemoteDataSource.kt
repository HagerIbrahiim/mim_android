package com.trianglz.mimar.modules.sign_up.data.remote

import com.trianglz.mimar.modules.sign_up.data.retrofit.request.SignUpRequestModel
import com.trianglz.mimar.modules.user.data.model.UserDataModel

interface SignUpRemoteDataSource {
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): UserDataModel
}