package com.trianglz.mimar.modules.branches.data.repository

import com.trianglz.mimar.modules.branches.data.remote.data_source.BranchesRemoteDataSource
import com.trianglz.mimar.modules.branches.data.remote.mapper.toDomainModel
import com.trianglz.mimar.common.data.mapper.toDomain
import com.trianglz.mimar.modules.branches.data.remote.mapper.toDomain
import com.trianglz.mimar.modules.branches.domain.model.BranchDetailsDomainModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.model.BranchReviewDomainModel
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import com.trianglz.mimar.modules.time.data.mapper.toDomainModel
import com.trianglz.mimar.modules.time.domain.model.TimeDomainModel
import com.trianglz.mimar.modules.lookups.data.mapper.toData
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationsDomainModel
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderDomainModel
import com.trianglz.mimar.modules.specilaities.data.mapper.toData
import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject


class BranchesRepositoryImpl @Inject constructor(private val remote: BranchesRemoteDataSource):
    BranchesRepository {

    private val modifiedBranches =
        MutableSharedFlow<BranchDomainModel>(0, Int.MAX_VALUE, BufferOverflow.SUSPEND)

    override suspend fun getFavoriteBranches(
        page: Int,
        perPage: Int,
        categoryId: Int?,
        ): List<BranchDomainModel> {
        val results = remote.getFavoriteBranches(
            page = page,
            perPage = perPage,
            categoryId = categoryId,
            ).map { it.toDomainModel() }
        return results;
    }

    override suspend fun getPopularBranches(
        page: Int,
        perPage: Int,
        lat: Double,
        lng: Double,
        name: String?,
        categoryId: Int?,
        servicedGenders: List<ServicedGenderDomainModel>?,
        offeredLocations: List<OfferedLocationsDomainModel>?,
        rating: Float?,
        specialties: List<SpecialtiesDomainModel>?,
        availableDate: LocalDate?,
        availableTime: LocalTime?,
        ): List<BranchDomainModel> {
        val results = remote.getPopularBranches(
            page = page,
            perPage = perPage,
            categoryId = categoryId,
            lat = lat,
            lng = lng,
            name = name,
            servicedGenders = servicedGenders?.map { it.toData() },
            offeredLocations = offeredLocations?.map { it.toData() },
            rating = rating,
            specialties = specialties?.map { it.toData() },
            availableDate = availableDate,
            availableTime = availableTime,
            ).map { it.toDomainModel() }
        return results;
    }

    override suspend fun toggleBranchFavourites(id: Int, isAddedToFavourites: Boolean) {
        val updatedItem = remote.toggleBranchFavourites(
            id, isAddedToFavourites
        )?.toDomainModel()
        updatedItem?.let {
            modifiedBranches.emit(updatedItem)
        }
    }

    override suspend fun getBranchFavouritesUpdates(): SharedFlow<BranchDomainModel> {
        return modifiedBranches
    }

    override suspend fun getBranchDetails(branchId: Int): BranchDetailsDomainModel {
        return remote.getBranchDetails(branchId).toDomain()
    }

    override suspend fun getBranchReviews(page: Int, perPage: Int,branchId: Int): List<BranchReviewDomainModel> {
        return remote.getBranchReviews(page,perPage, branchId).map { it.toDomain() }
    }

    override suspend fun getOtherBranchServiceProviderBranches(branchId: Int): List<BranchDomainModel> {
        return remote.getOtherBranchServiceProviderBranches(branchId).map { it.toDomainModel() }
    }

    override suspend fun getBranchesList(
        page: Int,
        perPage: Int,
        lat: Double,
        lng: Double,
        name: String?,
    ): List<BranchDomainModel> {
        return remote.getBranchesList(page, perPage, lat, lng, name).map { it.toDomainModel() }
    }

    override suspend fun getBranchAvailableSlots(
        branchId: Int,
        date: String
    ): List<TimeDomainModel> {
        return remote.getBranchAvailableSlots(branchId, date).map { it.toDomainModel() }
    }
}