package com.trianglz.mimar.modules.sign_in.data.mapper


import com.trianglz.mimar.modules.sign_in.data.retrofit.request.SocialLoginRequest
import com.trianglz.mimar.modules.sign_in.domain.model.SocialLoginDomainModel

fun SocialLoginDomainModel.toRequest(): SocialLoginRequest {
    return SocialLoginRequest(
        accessToken = accessToken,
        deviceId = deviceId,
        fcmToken = fcmToken,
        socialProviderId = socialProviderId,
        socialProviderType = socialProviderType,
        name = name,

    )
}