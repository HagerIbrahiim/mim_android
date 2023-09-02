package com.trianglz.mimar.modules.countries.domain.usecase

import com.trianglz.mimar.modules.countries.domain.model.CountryDomainModel
import com.trianglz.mimar.modules.countries.domain.repository.CountriesRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(private val repo: CountriesRepository) {

    suspend fun execute(
        page: Int = 1, perPage: Int = 10, searchString: String? = null,
    ): List<CountryDomainModel> {

        return repo.getCountriesList( page, perPage, searchString,)
    }


}