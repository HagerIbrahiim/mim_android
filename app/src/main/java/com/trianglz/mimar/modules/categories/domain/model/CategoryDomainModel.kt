package com.trianglz.mimar.modules.categories.domain.model

import com.trianglz.core.domain.model.IdIdentifiedModel


/**
 * Created by hassankhamis on 04,January,2023
 */

data class CategoryDomainModel(
    val id: Int,
    val image: String?,
    val name: String?,
):IdIdentifiedModel {
    override val uniqueId: Int
        get() = id
}
