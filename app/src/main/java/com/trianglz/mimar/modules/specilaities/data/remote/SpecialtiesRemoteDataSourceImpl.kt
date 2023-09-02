package com.trianglz.mimar.modules.specilaities.data.remote

import com.trianglz.mimar.modules.specilaities.data.model.BranchSpecialitiesDataModel
import com.trianglz.mimar.modules.specilaities.data.retrofit.service.SpecialtiesService
import com.trianglz.mimar.modules.specilaities.data.model.SpecialtiesDataModel
import javax.inject.Inject

class SpecialtiesRemoteDataSourceImpl @Inject constructor(
    private val specialtiesService: SpecialtiesService
) : SpecialtiesRemoteDataSource {


    override suspend fun getSpecialties(branchId: Int?): List<SpecialtiesDataModel> {
        return  specialtiesService.getSpecialtiesListAsync(branchId = branchId).specialties ?: listOf()
    }

    override suspend fun getBranchSpecialties(branchId: Int?): List<BranchSpecialitiesDataModel> {
        return  specialtiesService.getBranchSpecialtiesListAsync(branchId = branchId).specialties ?: listOf()

    }

}