package com.trianglz.mimar.modules.categories_list.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel
import com.trianglz.mimar.modules.categories_list.presentation.source.CategoriesSource
import kotlinx.coroutines.flow.MutableStateFlow

sealed class CategoriesEvent : BaseEvent {
    object RefreshScreen:  CategoriesEvent()
    data class OnCategoryClicked(val id: Int): CategoriesEvent()
}

sealed class CategoriesState : BaseState {
    object Idle : CategoriesState()
    data class OpenCategory(val id: Int): CategoriesState()
}
data class CategoriesViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val scource: MutableState<CategoriesSource?> = mutableStateOf(null),

    ): BaseViewState {
    val list: SnapshotStateList<CategoryUIModel> = mutableStateListOf()

    override val networkError: MutableStateFlow<Boolean> get() {
        return MutableStateFlow(false)
    }
}