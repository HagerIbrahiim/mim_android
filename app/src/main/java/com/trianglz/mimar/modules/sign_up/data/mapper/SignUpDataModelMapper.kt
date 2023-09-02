package com.trianglz.mimar.modules.sign_up.data.mapper

import com.trianglz.mimar.modules.sign_up.data.retrofit.request.SignUpRequestModel
import com.trianglz.mimar.modules.sign_up.domain.model.SignUpDomainModel

fun SignUpDomainModel.toRequest(): SignUpRequestModel {
    return SignUpRequestModel(phoneNumber, dialCode, password, deviceId, fcmToken,)
}