package com.trianglz.mimar.modules.branches.presentation.source

import android.app.Application
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.modules.branches.domain.usecase.GetPopularBranchesUseCase
import com.trianglz.mimar.modules.branches.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.offered_location.presentation.mapper.toDomain
import com.trianglz.mimar.modules.offered_location.presentation.model.OfferedLocationsUIModel
import com.trianglz.mimar.modules.serviced_genders.presenation.mapper.toDomain
import com.trianglz.mimar.modules.serviced_genders.presenation.model.ServicedGenderUIModel
import com.trianglz.mimar.modules.specilaities.presenation.mapper.toDomain
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class BranchesSource @Inject constructor(
    private val getBranchesUseCase: GetPopularBranchesUseCase,
    private val application: Application
) : ComposePaginatedListDataSource<BranchUIModel>(
    autoInit = true,
    shimmerList = BranchUIModel.getShimmerList()
) {

    var categoryId: Int? = null
    var lat: Double? = null
    var lng: Double? = null
    var searchString: String? = null
    var showCategories: Boolean = true
    var servicedGenders: List<ServicedGenderUIModel>? = null
    var offeredLocations: List<OfferedLocationsUIModel>? = null
    var rating: Float? = null
    var specialties: List<SpecialtiesUIModel>? = null
    var availableDate: LocalDate? = null
    var availableTime: LocalTime? = null
    var onFavoriteClick: ((item: BranchUIModel) -> Unit) = {  }
    var onClick: ((id: Int) -> Unit) = {  }
    override suspend fun getPage(page: Int, perPage: Int): List<BranchUIModel> {
//        Log.d("test_hassan", "getPage: page: $page, lat = $lat , categoryId = $categoryId")
        if (categoryId == null && showCategories) return BranchUIModel.getShimmerList()
        if (lat == null || lng == null) return emptyList()
        val res =  getBranchesUseCase.execute(
            page,
            perPage,
            lat = lat!!,
            lng = lng!!,
            categoryId = categoryId,
            name = searchString,
            specialties = specialties?.map { it.toDomain(application.baseContext) },
            rating = rating,
            offeredLocations = offeredLocations?.map { it.toDomain(application.baseContext) },
            availableTime = availableTime,
            availableDate = availableDate,
            servicedGenders = servicedGenders?.map { it.toDomain(application.baseContext) }).map { it.toUIModel(
            onFavoriteClick = onFavoriteClick,
            onClick = onClick
        )}

        return res
    }
}
