package com.trianglz.mimar.modules.categories.data.repository

import com.trianglz.mimar.modules.categories.data.remote.data_source.CategoriesRemoteDataSource
import com.trianglz.mimar.modules.categories.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.categories.domain.model.CategoryDomainModel
import com.trianglz.mimar.modules.categories.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(private val remote: CategoriesRemoteDataSource):
    CategoriesRepository {
    override suspend fun getCategories(page: Int, perPage: Int): List<CategoryDomainModel> {
        val results = remote.getCategories(page, perPage).map { it.toDomainModel() }
        return results;
    }
}