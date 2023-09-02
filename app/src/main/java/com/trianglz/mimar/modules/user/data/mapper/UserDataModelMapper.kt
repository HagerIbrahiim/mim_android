package com.trianglz.mimar.modules.user.data.mapper

import com.trianglz.mimar.modules.addresses.data.mapper.toDomain
import com.trianglz.mimar.modules.cart.data.remote.mapper.toDomain
import com.trianglz.mimar.modules.countries.data.mapper.toDomain
import com.trianglz.mimar.modules.user.data.model.UserDataModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel

fun UserDataModel.toDomainModel(): UserDomainModel {
    return UserDomainModel(
        id,
        email,
        firstName,
        lastName,
        image,
        isNotifiable,
        phoneNumber,
        isClassical,
        gender,
        status,
        deletedAt,
        phoneDialCode,
        isPhoneVerified,
        isProfileCompleted,
        mustChangePassword,
        isEmailVerified,
        dateOfBirth,
        authToken,
        appointments,
        country?.toDomain(),
        defaultAddress?.toDomain(),
        cart = cart?.toDomain(),
        unseenNotificationsCount = unseenNotificationsCount,
    )
}
