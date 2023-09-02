package com.trianglz.mimar.modules.ratings.domain.model

import com.trianglz.core.domain.model.StringWrapper

data class RatingDomainModel(
    val id: Int,
    val rating: Float,
    val title: StringWrapper,
    var isChecked: Boolean = false,
    val value: String?= null
)