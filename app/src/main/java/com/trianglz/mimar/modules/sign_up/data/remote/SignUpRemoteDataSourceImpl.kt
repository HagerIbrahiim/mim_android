package com.trianglz.mimar.modules.sign_up.data.remote

import com.trianglz.mimar.modules.sign_up.data.retrofit.request.SignUpRequestModel
import com.trianglz.mimar.modules.sign_up.data.retrofit.service.SignUpService
import com.trianglz.mimar.modules.user.data.model.UserDataModel
import javax.inject.Inject

class SignUpRemoteDataSourceImpl @Inject constructor(private val service: SignUpService) :
    SignUpRemoteDataSource {
    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): UserDataModel {
       return service.signUpAsync(signUpRequestModel).customer
    }
}