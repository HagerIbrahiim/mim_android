package com.trianglz.mimar.modules.categories.data.remote.data_source

import com.trianglz.mimar.modules.categories.data.remote.model.CategoryDataModel
import com.trianglz.mimar.modules.categories.data.remote.retrofit.service.CategoriesService
import kotlinx.coroutines.delay
import javax.inject.Inject

class CategoriesRemoteDataSourceImpl @Inject constructor(private val service: CategoriesService) :
    CategoriesRemoteDataSource {
    override suspend fun getCategories(page: Int, perPage: Int): List<CategoryDataModel> {
//        delay(20000)
        return service.getCategoriesListAsync(page, perPage).categories ?: emptyList()
    }
}