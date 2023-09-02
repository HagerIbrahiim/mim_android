package com.trianglz.mimar.modules.countries.presentation.source

import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.modules.countries.domain.usecase.GetCountriesUseCase
import com.trianglz.mimar.modules.countries.presentation.mapper.toUI
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel
import javax.inject.Inject

class CountriesListSource @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : ComposePaginatedListDataSource<CountryUIModel>(
        shimmerList = CountryUIModel.getShimmerList(),
        autoInit = false,
    ) {

    var searchString: String? = null

    override suspend fun getPage(page: Int, perPage: Int): List<CountryUIModel> {
       return getCountriesUseCase.execute(page, perPage, searchString,).map {
           it.toUI()
       }
    }


}