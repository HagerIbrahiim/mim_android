package com.trianglz.mimar.modules.ratings.presenation.mapper

import androidx.compose.runtime.mutableStateOf
import com.trianglz.mimar.modules.ratings.domain.model.RatingDomainModel
import com.trianglz.mimar.modules.ratings.presenation.model.RatingUIModel

fun RatingUIModel.toDomain()= RatingDomainModel(
    id, rating, title, isChecked.value,value
)

fun RatingDomainModel.toUI()= RatingUIModel(
    id, title = title, mutableStateOf(isChecked), value ?:"", rating,
)