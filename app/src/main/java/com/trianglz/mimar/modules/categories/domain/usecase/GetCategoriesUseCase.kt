package com.trianglz.mimar.modules.categories.domain.usecase

import com.trianglz.core.domain.usecase.BaseUseCase
import com.trianglz.mimar.modules.categories.domain.model.CategoryDomainModel
import com.trianglz.mimar.modules.categories.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repo: CategoriesRepository) :
    BaseUseCase {
    suspend fun execute(page: Int = 1, perPage: Int = 10): List<CategoryDomainModel> {
        return repo.getCategories(page, perPage)
    }
}