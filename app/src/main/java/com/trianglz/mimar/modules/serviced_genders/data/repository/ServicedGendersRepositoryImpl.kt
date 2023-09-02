package com.trianglz.mimar.modules.serviced_genders.data.repository

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.mimar.modules.lookups.data.mapper.toData
import com.trianglz.mimar.modules.lookups.data.mapper.toGenderDomainModel
import com.trianglz.mimar.modules.lookups.data.model.LookupsDataModel
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderDomainModel
import com.trianglz.mimar.modules.serviced_genders.data.remote.ServicedGenderRemoteDataSource
import com.trianglz.mimar.modules.serviced_genders.domain.repository.ServicedGendersRepository
import javax.inject.Inject

class ServicedGendersRepositoryImpl @Inject constructor(
    private val servicedGenderRemoteDataSource: ServicedGenderRemoteDataSource
) : ServicedGendersRepository {


    private val servicedGenderList = ArrayList<ServicedGenderDomainModel>()

    override suspend fun getServicedGender(): List<ServicedGenderDomainModel> {

        return if(servicedGenderList.isEmpty()  ) {
            val  list = servicedGenderRemoteDataSource.getServicedGender().mapIndexed {
                    index, item -> item.toGenderDomainModel(index) }
            servicedGenderList.addAll(list)
            list
        } else servicedGenderList
    }


    override fun submitServicedGenders(servicedGenders: List<ServicedGenderDomainModel>) {
        val newList = SnapshotStateList<ServicedGenderDomainModel>()

        servicedGenders.forEach { item ->
            newList.add(item)
        }
        servicedGenderList.clear()
        servicedGenderList.addAll(newList)
    }

    override fun resetServicedGendersData() {
        servicedGenderList.clear()
    }


}