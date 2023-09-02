package com.trianglz.mimar.modules.countries.data.repository

import com.trianglz.mimar.modules.countries.data.mapper.toDomain
import com.trianglz.mimar.modules.countries.data.remote.CountriesRemoteDataSource
import com.trianglz.mimar.modules.countries.domain.model.CountryDomainModel
import com.trianglz.mimar.modules.countries.domain.repository.CountriesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class CountriesRepositoryImpl @Inject constructor(
    private val countriesRemoteDataSource: CountriesRemoteDataSource
) :
    CountriesRepository {

    override suspend fun getCountriesList(
        page: Int,
        perPage: Int,
        searchString: String?,
    ): List<CountryDomainModel> {
        return countriesRemoteDataSource.getCountriesList(
            page,
            perPage,
            searchString,
        ).map {
            it.toDomain()
        }
    }

}
