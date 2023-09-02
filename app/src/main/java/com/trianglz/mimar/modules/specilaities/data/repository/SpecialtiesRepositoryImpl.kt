package com.trianglz.mimar.modules.specilaities.data.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.mimar.modules.specilaities.data.mapper.toData
import com.trianglz.mimar.modules.specilaities.data.mapper.toDomain
import com.trianglz.mimar.modules.specilaities.data.model.SpecialtiesDataModel
import com.trianglz.mimar.modules.specilaities.data.remote.SpecialtiesRemoteDataSource
import com.trianglz.mimar.modules.specilaities.domain.model.SpecialtiesDomainModel
import com.trianglz.mimar.modules.specilaities.domain.repository.SpecialtiesRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class SpecialtiesRepositoryImpl @Inject constructor(
    private val specialtiesRemoteDataSource: SpecialtiesRemoteDataSource
) : SpecialtiesRepository {


    private val specialtiesList = ArrayList<SpecialtiesDomainModel>()

    override suspend fun getSpecialties(branchId: Int?): List<SpecialtiesDomainModel> {

        return if(specialtiesList.isEmpty()  ) {

            val list = specialtiesRemoteDataSource.getSpecialties(branchId).map { it.toDomain() }

            specialtiesList.addAll(list)
            list
        } else specialtiesList

    }

    override suspend fun getBranchSpecialties(branchId: Int?): List<SpecialtiesDomainModel> {
        return specialtiesRemoteDataSource.getBranchSpecialties(branchId).map { it.toDomain() }
    }
    override fun submitSpecialties(specialties: List<SpecialtiesDomainModel>) {
        val newList = SnapshotStateList<SpecialtiesDomainModel>()

        specialties.forEach { item ->
            newList.add(item)
        }
        specialtiesList.clear()
        specialtiesList.addAll(newList)
    }

    override fun clearSpecialities() {
        specialtiesList.clear()
    }




}