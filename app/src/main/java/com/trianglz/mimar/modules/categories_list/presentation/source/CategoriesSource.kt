package com.trianglz.mimar.modules.categories_list.presentation.source

import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.modules.categories.domain.usecase.GetCategoriesUseCase
import com.trianglz.mimar.modules.categories.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel
import javax.inject.Inject

class CategoriesSource @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
)  : ComposePaginatedListDataSource<CategoryUIModel>(
    perPage = 40,
    autoInit = false,
    shimmerList = CategoryUIModel.getShimmerList(40)
) {

    override suspend fun getPage(page: Int, perPage: Int): List<CategoryUIModel> {
        return  getCategoriesUseCase.execute(page, perPage).mapIndexed { index, categoryDomainModel ->
            categoryDomainModel.toUIModel(index = index)
        }

    }
}