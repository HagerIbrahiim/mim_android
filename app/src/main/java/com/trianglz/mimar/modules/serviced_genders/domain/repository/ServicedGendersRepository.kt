package com.trianglz.mimar.modules.serviced_genders.domain.repository

import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderDomainModel

interface ServicedGendersRepository {

    suspend fun getServicedGender(): List<ServicedGenderDomainModel>
    fun submitServicedGenders(servicedGenders: List<ServicedGenderDomainModel>)
    fun resetServicedGendersData()

}