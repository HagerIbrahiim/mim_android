package com.trianglz.mimar.modules.ratings.presenation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel

data class RatingUIModel(
    override val id: Int,
    override val title: StringWrapper = StringWrapper(""),
    override val isChecked: MutableState<Boolean> = mutableStateOf(false),
    override val value: String,
    val rating: Float,
    override val showShimmer: Boolean = false,
    ): BaseCheckboxItemUiModel
