package com.trianglz.mimar.modules.categories.presentation.mapper

import com.trianglz.mimar.modules.categories.domain.model.CategoryDomainModel
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel.Companion.getCategoryColor

fun CategoryDomainModel.toUIModel(index: Int = 0) = CategoryUIModel(
    id = id,
    image = image ?: "",
    name = name ?: "",
    color = { index.getCategoryColor() },
)