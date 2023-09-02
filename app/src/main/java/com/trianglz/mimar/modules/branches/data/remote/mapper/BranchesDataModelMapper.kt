package com.trianglz.mimar.modules.branches.data.remote.mapper

import com.trianglz.mimar.common.data.mapper.toDomain
import com.trianglz.mimar.modules.branches.data.remote.model.BranchDataModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.categories.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.toOfferedLocationType
import com.trianglz.mimar.modules.branches.data.remote.model.BranchDetailsDataModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDetailsDomainModel
import com.trianglz.mimar.modules.branches.domain.model.BranchStatusType.Companion.toBranchStatusType
import com.trianglz.mimar.modules.serviced_genders.domain.model.getServicedGender

fun BranchDataModel.toDomainModel() = BranchDomainModel(
    id = id,
    image = image ?: "",
    name = name ?: "",
    location = location ?: "",
    rating = rating,
    isFavorite = isFavorite?: false,
    reviewsCount = reviewsCount,
    offeredLocation = offeredLocation.toOfferedLocationType(location),
    serviceProviderLogo = serviceProviderLogo,
    offDays = offDays ?: emptyList(),
    paymentMethods = paymentMethods,
    statusType = status.toBranchStatusType(),
)

fun BranchDetailsDataModel.toDomain() = BranchDetailsDomainModel(
    id = id,
    image = image ?: "",
    name = name ?:"",
    location = location ?:"",
    rating = rating,
    isFavorite = isFavorite ?: false,
    reviewsCount = reviewsCount,
    servicedGender = getServicedGender(servicedGender ?:""),
    buildingNumber = buildingNumber?:"",
    category = category?.toDomainModel(),
    categoryId = categoryId?: -1,
    city = city?:"",
    coveredZones = coveredZones?.map { it.toDomain() } ?: listOf(),
    lat = lat?: 0.0,
    lng = lng?: 0.0,
    offDays = offDays ?:listOf(),
    paymentMethods = paymentMethods ?:"",
    phoneNumber = phoneNumber?: "",
    serviceProviderId = serviceProviderId ?: -1,
    serviceProviderLogo = serviceProviderLogo?:"",
    status = status.toBranchStatusType(),
    street = street?:"",
    workingHours = workingHours?.map { it.toDomain() } ?: listOf(),
    offeredLocation = offeredLocation.toOfferedLocationType(location),

)