package com.trianglz.mimar.modules.branches.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.repository.BranchesRepository
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationsDomainModel
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderDomainModel
import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class GetPopularBranchesUseCase @Inject constructor(private val repo: BranchesRepository) :
    BaseUseCase {
    suspend fun execute(
        page: Int = 1,
        perPage: Int = 10,
        lat: Double,
        lng: Double,
        name: String? = null,
        categoryId: Int? = null,
        servicedGenders: List<ServicedGenderDomainModel>? = null,
        offeredLocations: List<OfferedLocationsDomainModel>? = null,
        rating: Float? = null,
        specialties: List<SpecialtiesDomainModel>? = null,
        availableDate: LocalDate?=null,
        availableTime: LocalTime?=null,
    ): List<BranchDomainModel> {
        return repo.getPopularBranches(
            page = page,
            perPage = perPage,
            categoryId = categoryId,
            lat = lat,
            lng = lng,
            name = name,
            servicedGenders = servicedGenders,
            offeredLocations = offeredLocations,
            rating = rating,
            specialties = specialties,
            availableTime = availableTime,
            availableDate = availableDate,

            )
    }
}