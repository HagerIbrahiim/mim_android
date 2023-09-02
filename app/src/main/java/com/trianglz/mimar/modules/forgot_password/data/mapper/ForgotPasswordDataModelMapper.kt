package com.trianglz.mimar.modules.forgot_password.data.mapper

import com.trianglz.mimar.modules.forgot_password.data.retrofit.request.ForgotPasswordRequestModel
import com.trianglz.mimar.modules.forgot_password.domain.model.ForgotPasswordDomainModel

fun ForgotPasswordDomainModel.toRequest(): ForgotPasswordRequestModel {
    return ForgotPasswordRequestModel(phoneNumber, dialCode,)
}