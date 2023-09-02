package com.trianglz.mimar.modules.countries.domain.repository

import com.trianglz.mimar.modules.countries.domain.model.CountryDomainModel

interface CountriesRepository {

    suspend fun getCountriesList(
        page: Int = 1, perPage: Int = 10, searchString: String? = null
    ): List<CountryDomainModel>



}