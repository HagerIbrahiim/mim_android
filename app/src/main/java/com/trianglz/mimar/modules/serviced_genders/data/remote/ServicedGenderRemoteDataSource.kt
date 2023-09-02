package com.trianglz.mimar.modules.serviced_genders.data.remote

import com.trianglz.mimar.modules.lookups.data.model.LookupsDataModel

interface ServicedGenderRemoteDataSource {

    suspend fun getServicedGender(): List<LookupsDataModel>

}