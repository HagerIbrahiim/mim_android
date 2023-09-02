package com.trianglz.mimar.modules.branches.data.remote.data_source

import com.trianglz.mimar.modules.branches.data.remote.model.BranchDataModel
import com.trianglz.mimar.modules.branches.data.remote.model.BranchDetailsDataModel
import com.trianglz.mimar.modules.branches.data.remote.model.BranchReviewDataModel
import com.trianglz.mimar.modules.time.data.model.TimeDataModel
import com.trianglz.mimar.modules.time.domain.model.TimeDomainModel
import com.trianglz.mimar.modules.lookups.data.model.LookupsDataModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationsDomainModel
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderDomainModel
import com.trianglz.mimar.modules.specilaities.data.model.SpecialtiesDataModel
import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel
import java.time.LocalDate
import java.time.LocalTime

interface BranchesRemoteDataSource {
    suspend fun getFavoriteBranches(
        page: Int,
        perPage: Int,
        categoryId: Int? = null,
    ): List<BranchDataModel>

    suspend fun getPopularBranches(
        page: Int,
        perPage: Int,
        lat: Double,
        lng: Double,
        name: String? = null,
        categoryId: Int? = null,
        servicedGenders: List<LookupsDataModel>? = null,
        offeredLocations: List<LookupsDataModel>? = null,
        rating: Float? = null,
        specialties: List<SpecialtiesDataModel>? = null,
        availableDate: LocalDate? = null,
        availableTime: LocalTime?= null,
    ): List<BranchDataModel>

    suspend fun toggleBranchFavourites(
        id: Int,
        isAddedToFavourites: Boolean,
    ): BranchDataModel?

    suspend fun getBranchDetails(branchId: Int): BranchDetailsDataModel

    suspend fun getBranchReviews(page: Int, perPage: Int,branchId: Int): List<BranchReviewDataModel>

    suspend fun getOtherBranchServiceProviderBranches(branchId: Int): List<BranchDataModel>


    suspend fun getBranchesList(
        page: Int,
        perPage: Int,
        lat: Double,
        lng: Double,
        name: String? = null,
    ): List<BranchDataModel>

    suspend fun getBranchAvailableSlots(
        branchId: Int, date: String,
    ): List<TimeDataModel>
}