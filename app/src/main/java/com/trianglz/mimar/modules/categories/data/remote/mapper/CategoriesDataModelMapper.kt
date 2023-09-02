package com.trianglz.mimar.modules.categories.data.remote.mapper

import com.trianglz.mimar.modules.categories.data.remote.model.CategoryDataModel
import com.trianglz.mimar.modules.categories.domain.model.CategoryDomainModel

fun CategoryDataModel.toDomainModel() = CategoryDomainModel(
    id = id,
    image = image,
    name = title,
)