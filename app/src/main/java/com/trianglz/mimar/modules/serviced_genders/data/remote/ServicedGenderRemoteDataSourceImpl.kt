package com.trianglz.mimar.modules.serviced_genders.data.remote

import com.trianglz.mimar.modules.lookups.data.model.LookupsDataModel
import com.trianglz.mimar.modules.serviced_genders.data.retrofit.service.ServicedGendersService
import javax.inject.Inject

class ServicedGenderRemoteDataSourceImpl @Inject constructor(
    private val service: ServicedGendersService
) : ServicedGenderRemoteDataSource {

    override suspend fun getServicedGender(): List<LookupsDataModel> {
        return service.getServicedGendersListAsync(gendersPage = -1).data?.get(0)?.genders ?: listOf()
    }

}