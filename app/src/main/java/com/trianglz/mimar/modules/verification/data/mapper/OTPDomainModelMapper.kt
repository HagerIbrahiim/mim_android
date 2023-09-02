package com.trianglz.mimar.modules.verification.data.mapper

import com.trianglz.mimar.modules.verification.data.retrofit.request.OTPRequestModel
import com.trianglz.mimar.modules.verification.domain.model.OTPDomainModel

fun OTPDomainModel.toRequest(): OTPRequestModel {
    return OTPRequestModel(otp,)
}