package com.trianglz.mimar.modules.categories.data.remote.data_source

import com.trianglz.mimar.modules.categories.data.remote.model.CategoryDataModel

interface CategoriesRemoteDataSource {
    suspend fun getCategories(page: Int, perPage: Int): List<CategoryDataModel>
}