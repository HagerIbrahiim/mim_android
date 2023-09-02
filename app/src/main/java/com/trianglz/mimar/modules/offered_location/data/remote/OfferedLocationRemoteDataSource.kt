package com.trianglz.mimar.modules.offered_location.data.remote

import com.trianglz.mimar.modules.lookups.data.model.LookupsDataModel

interface OfferedLocationRemoteDataSource {

    suspend fun getOfferedLocations(): List<LookupsDataModel>
}