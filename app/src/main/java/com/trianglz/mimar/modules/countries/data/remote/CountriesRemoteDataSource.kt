package com.trianglz.mimar.modules.countries.data.remote

import com.trianglz.mimar.modules.countries.data.model.CountryDataModel

interface CountriesRemoteDataSource {

    suspend fun getCountriesList(
        page: Int = 1, perPage: Int = 10, searchString: String? = null,
    ): List<CountryDataModel>


}