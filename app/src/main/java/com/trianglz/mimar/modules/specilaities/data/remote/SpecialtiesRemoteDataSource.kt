package com.trianglz.mimar.modules.specilaities.data.remote

import com.trianglz.mimar.modules.specilaities.data.model.BranchSpecialitiesDataModel
import com.trianglz.mimar.modules.specilaities.data.model.SpecialtiesDataModel

interface SpecialtiesRemoteDataSource {

    suspend fun getSpecialties(branchId: Int?): List<SpecialtiesDataModel>
    suspend fun getBranchSpecialties(branchId: Int?): List<BranchSpecialitiesDataModel>

}