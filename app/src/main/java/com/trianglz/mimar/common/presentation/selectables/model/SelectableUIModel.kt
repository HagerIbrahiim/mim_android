package com.trianglz.mimar.common.presentation.selectables.model

import androidx.compose.runtime.MutableState
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.pagination.model.ShimmerModel


interface SelectableUIModel: ShimmerModel {
    val title: StringWrapper
    val isChecked: MutableState<Boolean>
}