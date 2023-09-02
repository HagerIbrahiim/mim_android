package com.trianglz.mimar.modules.offered_location.data.remote

import com.trianglz.mimar.modules.lookups.data.model.LookupsDataModel
import com.trianglz.mimar.modules.offered_location.data.retrofit.service.OfferedLocationService
import javax.inject.Inject

class OfferedLocationRemoteDataSourceImpl @Inject constructor(
    private val offeredLocationService: OfferedLocationService
) : OfferedLocationRemoteDataSource {


    override suspend fun getOfferedLocations(): List<LookupsDataModel> {
        return offeredLocationService.getOfferedLocationListAsync(offeredLocationPage = -1).data?.get(0)?.offeredLocations ?: listOf()
    }

}