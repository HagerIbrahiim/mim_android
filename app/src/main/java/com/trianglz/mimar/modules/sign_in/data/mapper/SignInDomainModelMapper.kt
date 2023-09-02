package com.trianglz.mimar.modules.sign_in.data.mapper

import com.trianglz.mimar.modules.sign_in.data.retrofit.request.SignInRequestModel
import com.trianglz.mimar.modules.sign_in.domain.model.SignInDomainModel

fun SignInDomainModel.toRequest(): SignInRequestModel {
    return SignInRequestModel(phoneNumber, password, deviceId, fcmToken, dialCode)
}