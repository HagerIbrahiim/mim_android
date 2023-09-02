package com.trianglz.mimar.modules.branches.data.remote.data_source

import com.trianglz.core.domain.extensions.toIsoFormat
import com.trianglz.mimar.common.domain.extention.toHourMinutes24Format
import com.trianglz.mimar.modules.branches.data.remote.model.BranchDataModel
import com.trianglz.mimar.modules.branches.data.remote.model.BranchDetailsDataModel
import com.trianglz.mimar.modules.branches.data.remote.model.BranchReviewDataModel
import com.trianglz.mimar.modules.branches.data.remote.retrofit.service.BranchesService
import com.trianglz.mimar.modules.lookups.data.mapper.toGenderDomainModel
import com.trianglz.mimar.modules.lookups.data.mapper.toOfferedLocationDomain
import com.trianglz.mimar.modules.time.data.model.TimeDataModel
import com.trianglz.mimar.modules.lookups.data.model.LookupsDataModel
import com.trianglz.mimar.modules.specilaities.data.model.SpecialtiesDataModel
import com.trianglz.mimar.modules.offered_location.domain.model.getOfferedLocationFilterKey
import com.trianglz.mimar.modules.serviced_genders.domain.model.getServicedGenderFilterKey
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class BranchesRemoteDataSourceImpl @Inject constructor(private val service: BranchesService) :
    BranchesRemoteDataSource {


    override suspend fun getFavoriteBranches(
        page: Int,
        perPage: Int,
        categoryId: Int?,
    ): List<BranchDataModel> {
        return service.getFavoriteBranchesListAsync(
            page = page,
            items = perPage,
            categoryId = categoryId,
        ).branches ?: emptyList()
    }

    override suspend fun getPopularBranches(
        page: Int,
        perPage: Int,
        lat: Double,
        lng: Double,
        name: String?,
        categoryId: Int?,
        servicedGenders: List<LookupsDataModel>?,
        offeredLocations: List<LookupsDataModel>?,
        rating: Float?,
        specialties: List<SpecialtiesDataModel>?,
        availableDate: LocalDate?,
        availableTime: LocalTime?,
        ): List<BranchDataModel> {
        val servicedGender = servicedGenders?.mapIndexed { index, item ->
            item.toGenderDomainModel(index)
        }?.getServicedGenderFilterKey()
        val offeredLocation = offeredLocations?.mapIndexed { index, item ->
            item.toOfferedLocationDomain(index)
        }?.getOfferedLocationFilterKey()
        val specialitiesIds = specialties?.joinToString(",") { it.id.toString() }


        return service.getBranchesListAsync(
            page = page,
            items = perPage,
            categoryId = categoryId,
            lat = lat,
            lng = lng,
            name = name,
            servicedGender = servicedGender,
            offeredLocation = offeredLocation,
            rating = rating,
            specialitiesIds = specialitiesIds,
            availableDate = availableDate?.toIsoFormat(),
            availableTime = availableTime?.toHourMinutes24Format(),
        ).branches ?: emptyList()
    }

    override suspend fun toggleBranchFavourites(
        id: Int,
        isAddedToFavourites: Boolean
    ): BranchDataModel? {
        return service.toggleBranchFavoriteAsync(id).branch
        //TODO
//        return BranchDataModel(
//            id = id,
//            name = "Branch Name Dummy Text  $id",
//            image = "https://www.technipages.com/wp-content/uploads/2019/07/Cover-600x371.jpg",
//            location = "Branch Location Dummy",
//            rating = 4.2f,
//            reviewsCount = 9,
//            isFavorite = !isAddedToFavourites
//        )
    }

    override suspend fun getBranchDetails(branchId: Int): BranchDetailsDataModel {
       return service.getBranchDetails(branchId).branch ?: BranchDetailsDataModel()
    }

    override suspend fun getBranchReviews(page: Int, perPage: Int,branchId: Int): List<BranchReviewDataModel> {
        return service.getBranchesReviews(page,perPage,branchId).reviews ?: listOf()
    }

    override suspend fun getOtherBranchServiceProviderBranches(branchId: Int): List<BranchDataModel> {
        return service.getOtherBranchServiceProviderBranches(branchId).branches  ?: listOf()

    }

    override suspend fun getBranchesList(
        page: Int,
        perPage: Int,
        lat: Double,
        lng: Double,
        name: String?,

    ): List<BranchDataModel> {
        return service.getBranchesListAsync(
            page = page,
            items = perPage,
            lat = lat,
            lng = lng,
            name = name,
        ).branches ?: emptyList()
    }

    override suspend fun getBranchAvailableSlots(branchId: Int, date: String): List<TimeDataModel> {
        return service.getBranchAvailableSlotsAsync(branchId, date).timeSlots ?: emptyList()
    }
}