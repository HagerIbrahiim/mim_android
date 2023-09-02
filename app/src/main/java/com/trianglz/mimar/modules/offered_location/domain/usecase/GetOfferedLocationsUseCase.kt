package com.trianglz.mimar.modules.offered_location.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationsDomainModel
import com.trianglz.mimar.modules.offered_location.domain.repository.OfferedLocationsRepository
import javax.inject.Inject

class GetOfferedLocationsUseCase @Inject constructor(private val repo: OfferedLocationsRepository) :
    BaseUseCase {
    suspend fun execute(): List<OfferedLocationsDomainModel> {
        return repo.getOfferedLocations()
    }
}