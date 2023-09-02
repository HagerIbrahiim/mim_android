package com.trianglz.mimar.modules.ratings.presenation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class RatingAppointmentUIModel(
    val id: Int,
    val name: String,
    val rating: MutableState<Float> = mutableStateOf(0F),
    val onValueChange: (Float) -> Unit = {}
)