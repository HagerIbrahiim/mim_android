package com.trianglz.mimar.common.presentation.tabs.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import com.trianglz.core.domain.model.StringWrapper

data class TabItemUIModel(
    val title: StringWrapper,
    @DrawableRes val image: Int,
    val isSelected: MutableState<Boolean>,
)