package com.trianglz.mimar.modules.categories.domain.repository

import com.trianglz.mimar.modules.categories.domain.model.CategoryDomainModel

interface CategoriesRepository {
    suspend fun getCategories(page: Int, perPage: Int): List<CategoryDomainModel>
}