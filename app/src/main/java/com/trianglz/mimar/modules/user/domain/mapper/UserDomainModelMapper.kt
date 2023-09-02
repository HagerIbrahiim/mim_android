package com.trianglz.mimar.modules.user.domain.mapper

import com.trianglz.mimar.modules.user.data.retrofit.request.UpdateUserRequestModel
import com.trianglz.mimar.modules.user.domain.model.UpdateUserDomainModel

fun UpdateUserDomainModel.toDataModel(): UpdateUserRequestModel {
    return UpdateUserRequestModel(
        image,
        firstName,
        lastName,
        email,
        dateOfBirth,
        gender,
        deviceId,
        fcmToken,
        dialCode,
        phoneNumber,
        pushNotification,
    )
}
