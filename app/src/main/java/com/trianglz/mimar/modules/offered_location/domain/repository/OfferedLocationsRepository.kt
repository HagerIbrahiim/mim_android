package com.trianglz.mimar.modules.offered_location.domain.repository

import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationsDomainModel

interface OfferedLocationsRepository {

    suspend fun getOfferedLocations(): List<OfferedLocationsDomainModel>
    fun submitOfferedLocations(offeredLocations: List<OfferedLocationsDomainModel>)
    fun resetOfferedLocations()

}