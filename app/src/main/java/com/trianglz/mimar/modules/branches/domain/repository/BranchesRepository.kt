package com.trianglz.mimar.modules.branches.domain.repository

import com.trianglz.mimar.modules.branches.domain.model.BranchDetailsDomainModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.model.BranchReviewDomainModel
import com.trianglz.mimar.modules.time.domain.model.TimeDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationsDomainModel
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderDomainModel
import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel
import kotlinx.coroutines.flow.SharedFlow
import java.time.LocalDate
import java.time.LocalTime

interface BranchesRepository {
    suspend fun getFavoriteBranches(
        page: Int,
        perPage: Int,
        categoryId: Int? = null,
    ): List<BranchDomainModel>
    suspend fun getPopularBranches(
        page: Int,
        perPage: Int,
        lat: Double,
        lng: Double,
        name: String? = null,
        categoryId: Int? = null,
        servicedGenders: List<ServicedGenderDomainModel>? = null,
        offeredLocations: List<OfferedLocationsDomainModel>? = null,
        rating: Float? = null,
        specialties: List<SpecialtiesDomainModel>? = null,
        availableDate: LocalDate?,
        availableTime: LocalTime?,
    ): List<BranchDomainModel>
    suspend fun toggleBranchFavourites(
        id: Int,
        isAddedToFavourites: Boolean,
    )

    suspend fun getBranchFavouritesUpdates(): SharedFlow<BranchDomainModel>
    suspend fun getBranchDetails(branchId: Int): BranchDetailsDomainModel
    suspend fun getOtherBranchServiceProviderBranches(branchId: Int): List<BranchDomainModel>


    suspend fun getBranchReviews(page: Int, perPage: Int,branchId: Int): List<BranchReviewDomainModel>


    suspend fun getBranchesList(
        page: Int,
        perPage: Int,
        lat: Double,
        lng: Double,
        name: String? = null,
    ): List<BranchDomainModel>

    suspend fun getBranchAvailableSlots(
        branchId: Int, date: String,
    ): List<TimeDomainModel>

}