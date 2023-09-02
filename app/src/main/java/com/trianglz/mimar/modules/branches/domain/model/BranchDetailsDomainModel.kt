package com.trianglz.mimar.modules.branches.domain.model

import com.trianglz.mimar.common.domain.model.CoveredZonesDomainModel
import com.trianglz.mimar.common.domain.model.WorkingHoursDomainModel
import com.trianglz.mimar.modules.categories.domain.model.CategoryDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderType


data class BranchDetailsDomainModel(
    override val id: Int = -1,
    override val name: String = "",
    override val image: String = "",
    override val location: String = "",
    override val reviewsCount: Int? = null,
    override val rating: Float? = null,
    override val isFavorite: Boolean = false,
    override val  offeredLocation: OfferedLocationType = OfferedLocationType.Home,
    override val serviceProviderLogo: String="",
    override val offDays: List<String> = emptyList(),
    override val paymentMethods: String,
    val phoneNumber: String,
    val servicedGender: ServicedGenderType? = ServicedGenderType.Both,
    val serviceProviderId: Int,
    val categoryId: Int,
    val status: BranchStatusType,
    val city: String,
    val buildingNumber: String,
    val street: String,
    val lat: Double,
    val lng: Double,
    val category: CategoryDomainModel?,
    val workingHours: List<WorkingHoursDomainModel>,
    val coveredZones: List<CoveredZonesDomainModel>,
    ): BranchDomainModel()