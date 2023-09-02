package com.trianglz.mimar.modules.offered_location.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationsDomainModel
import com.trianglz.mimar.modules.offered_location.domain.repository.OfferedLocationsRepository
import javax.inject.Inject

class SubmitOfferedLocationsUseCase @Inject constructor(private val repo: OfferedLocationsRepository) :
    BaseUseCase {
    fun execute(offeredLocations: List<OfferedLocationsDomainModel>){
        repo.submitOfferedLocations(offeredLocations)
    }
}