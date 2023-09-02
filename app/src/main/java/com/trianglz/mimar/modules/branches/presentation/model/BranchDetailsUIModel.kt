package com.trianglz.mimar.modules.branches.presentation.model

import com.trianglz.mimar.common.presentation.models.CoveredZonesUIModel
import com.trianglz.mimar.common.presentation.models.WorkingHoursUIModel
import com.trianglz.mimar.modules.branches.domain.model.BranchStatusType
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderType

data class BranchDetailsUIModel(
    override val id: Int = 0,
    override val name: String = "",
    override val image: String = "",
    override val location: String = "",
    override val reviewsCount: Int = 0,
    override val rating: Float? = null,
    override val isFavorite: Boolean = false,
    override val onFavoriteClick: (item: BranchUIModel) -> Unit = {},
    override val offeredLocation: OfferedLocationType = OfferedLocationType.Home,
    override val offDays: List<String> = listOf(),
    override val serviceProviderLogo: String="",
    override val paymentMethods: String? = null,
    override val onClick: (id: Int) -> Unit = {},
    val phoneNumber: String = "",
    val servicedGenderType: ServicedGenderType? = ServicedGenderType.Both,
    val serviceProviderId: Int? = null,
    val categoryId: Int? = null,
    val status: BranchStatusType? = null,
    val city: String? = null,
    val buildingNumber: String? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val category: CategoryUIModel? = null,
    val workingHours: List<WorkingHoursUIModel>? = null,
    val coveredZones: List<CoveredZonesUIModel>? = null,
) : BranchUIModel()