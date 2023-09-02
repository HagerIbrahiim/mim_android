package com.trianglz.mimar.modules.user.presentaion.mapper

import android.content.Context
import com.trianglz.mimar.modules.addresses.ui.mapper.toDomain
import com.trianglz.mimar.modules.addresses.ui.mapper.toUI
import com.trianglz.mimar.modules.cart.presentation.mapper.toUI
import com.trianglz.mimar.modules.countries.presentation.mapper.toDomain
import com.trianglz.mimar.modules.countries.presentation.mapper.toUI
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel

/**
 * Created by hassankhamis on 04,January,2023
 */

fun UserDomainModel.toUIModel(onClick: (id: Int) -> Unit = {}) = UserUIModel(
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
    appointments,
    country?.toUI(),
    defaultAddress?.toUI(),
    cart?.toUI(),
    unseenNotificationsCount = unseenNotificationsCount,
    isSetCurrentLocation = isSetCurrentLocation,
    onClick = onClick,

    )

fun UserUIModel.toDomain(context: Context) = UserDomainModel(
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
    unseenNotificationsCount = unseenNotificationsCount,
    appointments = appointments,
    country = country?.toDomain(),
    defaultAddress = defaultAddress?.toDomain(context),
    isSetCurrentLocation = isSetCurrentLocation,

    )