package com.trianglz.mimar.modules.countries.data.remote

import com.trianglz.mimar.modules.countries.data.model.CountryDataModel
import com.trianglz.mimar.modules.countries.data.retrofit.service.CountriesApisService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class CountriesRemoteDataSourceImpl @Inject constructor(
    private val countriesApisService: CountriesApisService

) : CountriesRemoteDataSource {

    override suspend fun getCountriesList(
        page: Int, perPage: Int, searchString: String?,
    ): List<CountryDataModel> {

        return  countriesApisService.getCountriesListAsync(page,perPage,searchString).countries

    }
}