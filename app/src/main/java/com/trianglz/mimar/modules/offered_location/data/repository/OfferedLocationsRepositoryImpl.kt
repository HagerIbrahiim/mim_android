package com.trianglz.mimar.modules.offered_location.data.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.mimar.modules.lookups.data.mapper.toData
import com.trianglz.mimar.modules.lookups.data.mapper.toOfferedLocationDomain
import com.trianglz.mimar.modules.lookups.data.model.LookupsDataModel
import com.trianglz.mimar.modules.offered_location.data.remote.OfferedLocationRemoteDataSource
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationsDomainModel
import com.trianglz.mimar.modules.offered_location.domain.repository.OfferedLocationsRepository
import javax.inject.Inject

class OfferedLocationsRepositoryImpl @Inject constructor(
    private val offeredLocationRemoteDataSource: OfferedLocationRemoteDataSource
) : OfferedLocationsRepository {

    private val offeredLocationList = ArrayList<OfferedLocationsDomainModel>()

    override suspend fun getOfferedLocations(): List<OfferedLocationsDomainModel> {
        return if(offeredLocationList.isEmpty()) {
            val  list  = offeredLocationRemoteDataSource.getOfferedLocations().mapIndexed {
                    index, item -> item.toOfferedLocationDomain(index) }
            offeredLocationList.addAll(list)
            list
        } else offeredLocationList
    }


    override fun submitOfferedLocations(offeredLocations: List<OfferedLocationsDomainModel>) {
        val newList = SnapshotStateList<OfferedLocationsDomainModel>()

        offeredLocations.forEach { item ->
            newList.add(item)
        }
        offeredLocationList.clear()
        offeredLocationList.addAll(newList)
    }

    override fun resetOfferedLocations() {
        offeredLocationList.clear()
    }


}