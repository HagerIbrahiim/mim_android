package com.trianglz.mimar.modules.branches.presentation.mapper

import com.trianglz.mimar.common.presentation.mapper.toUI
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.categories.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDetailsDomainModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchDetailsUIModel

fun BranchDomainModel.toUIModel(onFavoriteClick: (item: BranchUIModel) -> Unit, onClick: (id: Int) -> Unit) = BranchUIModel(
    id = id,
    image = image,
    name = name,
    location = location,
    rating = rating,
    reviewsCount = reviewsCount ?: 0,
    offeredLocation = offeredLocation,
    onFavoriteClick = onFavoriteClick,
    onClick = onClick,
    isFavorite = isFavorite,
    serviceProviderLogo = serviceProviderLogo ?:"",
    offDays = offDays,
    paymentMethods = paymentMethods,
    statusType = statusType,
)

fun BranchUIModel.toDomainModel() = BranchDomainModel(
    id = id,
    image = image,
    name = name,
    location = location,
    rating = rating,
    reviewsCount = reviewsCount,
    isFavorite = isFavorite,
    offeredLocation = offeredLocation,
)

fun BranchDetailsDomainModel.toUI(onFavoriteClick: (item: BranchUIModel) -> Unit = {},
                                  onClick: (id: Int) -> Unit = {}) = BranchDetailsUIModel(
    id = id,
    image = image,
    name = name,
    location = location,
    rating = rating,
    isFavorite = isFavorite,
    reviewsCount = reviewsCount ?:0,
    onFavoriteClick = onFavoriteClick,
    servicedGenderType = servicedGender,
    buildingNumber = buildingNumber,
    category = category?.toUIModel(),
    categoryId = categoryId,
    city = city,
    coveredZones = coveredZones.map { it.toUI() },
    lat = lat,
    lng = lng,
    offDays = offDays,
    paymentMethods = paymentMethods,
    phoneNumber = phoneNumber,
    serviceProviderId = serviceProviderId,
    serviceProviderLogo = serviceProviderLogo,
    status = status,
    workingHours = workingHours.map { it.toUI() },
    offeredLocation = offeredLocation,
    onClick = onClick,
)