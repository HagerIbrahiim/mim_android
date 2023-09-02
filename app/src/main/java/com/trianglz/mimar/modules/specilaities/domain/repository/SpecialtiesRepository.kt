package com.trianglz.mimar.modules.specilaities.domain.repository

import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel
import kotlinx.coroutines.flow.Flow

interface SpecialtiesRepository {

    suspend fun getSpecialties(branchId: Int?): List<SpecialtiesDomainModel>
    suspend fun getBranchSpecialties(branchId: Int?): List<SpecialtiesDomainModel>

    fun submitSpecialties(specialties: List<SpecialtiesDomainModel>)
    fun clearSpecialities()
}