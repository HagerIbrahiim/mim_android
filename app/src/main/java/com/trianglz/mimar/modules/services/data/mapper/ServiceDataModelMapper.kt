package com.trianglz.mimar.modules.services.data.mapper

import com.trianglz.mimar.modules.branches.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.currency.data.mapper.toDomain
import com.trianglz.mimar.modules.services.data.model.ServiceDataModel
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel

fun ServiceDataModel.toDomain() = ServiceDomainModel(
    id = id,
    feesFrom = feesFrom,
    feesTo = feesTo,
    durationMins = durationMins,
    description = description,
    offeredLocation = offeredLocation,
    isActive = isActive,
    title = title,
    isAdded = isInCart,
    currency = currency?.toDomain(),
    branch = branch?.toDomainModel(),
    branchSpecialtyId = branchSpecialtyId,
)