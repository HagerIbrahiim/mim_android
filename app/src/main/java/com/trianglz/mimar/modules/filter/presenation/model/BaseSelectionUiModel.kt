package com.trianglz.mimar.modules.filter.presenation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.domain.model.StringWrapper

data class BaseSelectionUiModel(
    val title: StringWrapper,
    val items: SnapshotStateList<out BaseCheckboxItemUiModel>?,
    val selectAll: MutableState<Boolean> = mutableStateOf(false),
    val selectionType: SelectionType = SelectionType.MultiSelection
)