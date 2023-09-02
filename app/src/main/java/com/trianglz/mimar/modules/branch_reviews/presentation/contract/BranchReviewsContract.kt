package com.trianglz.mimar.modules.branch_reviews.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import kotlinx.coroutines.flow.MutableStateFlow

sealed class BranchReviewsEvent : BaseEvent {
    object RefreshScreen : BranchReviewsEvent()
}

sealed class BranchReviewsState: BaseState {
}

data class BranchReviewsViewState(
    val branchName: MutableState<String?> = mutableStateOf(null),
    val rating: MutableState<String?> = mutableStateOf(null),
    val numOfReviews: MutableState<String?> = mutableStateOf(null),
    val branchId: MutableState<Int?> = mutableStateOf(null),

    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
): BaseViewState