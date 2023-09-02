package com.trianglz.mimar.common.presentation.pagination

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.composables.CircularIndicator
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents.Companion.LocalMainComponent
import com.trianglz.core_compose.presentation.pagination.model.PaginatedModel
import com.trianglz.core_compose.presentation.pagination.model.StickyModel
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.core_compose.presentation.swipe_refresh.ComposeSwipeRefresh


/**
 * Will be removed
 * Just to add missing param @param[swipeEnabled], @param[networkErrorModifier], @param[disableScrollToFirstItem]  until core is updated
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : PaginatedModel> ComposePaginatedList(
    paginatedListSource: ComposePaginatedListDataSource<T>,
    lazyListModifier: Modifier = Modifier.fillMaxSize(),
    networkErrorModifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    state: LazyListState = rememberLazyListState(),
    isVertical: Boolean = true,
    spacingBetweenItems: Dp = 0.dp,
    swipeEnabled: Boolean = true,
    onRefresh: () -> Unit = { paginatedListSource.refreshAll() },
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    itemsBeforeList: (LazyListScope.() -> Unit)? = null,
    indicatorColor: Color = MaterialTheme.colors.primary,
    showErrorWhenOccurred: Boolean = true,
    disableScrollToFirstItem: Boolean = false,
    listItem: @Composable LazyItemScope.(index: Int, T) -> Unit,
    emptyListPlaceholder: @Composable LazyItemScope.() -> Unit,
) {
    /**
     * LaunchedEffect here fix bug when load new list sometimes first item disappear and need to scroll manual
     */
    LaunchedEffect(
        key1 = paginatedListSource.loadingState.value.doneInitial
    ) {
        if (paginatedListSource.loadingState.value.doneInitial) {
            if(disableScrollToFirstItem.not())state.scrollToItem(0)
        }
    }

    val currentList = remember(paginatedListSource.dataList.value) {
        paginatedListSource.dataList.value.getList() ?: SnapshotStateList()
    }

    val showNetworkError = remember(paginatedListSource.loadingState.value.failedInitial) {
        showErrorWhenOccurred && paginatedListSource.loadingState.value.failedInitial
    }
    val showRetryLastPage = remember(paginatedListSource.loadingState.value.failedNonInitial) {
        paginatedListSource.loadingState.value.failedNonInitial
    }
    val showPlaceHolder = remember(currentList) {
        currentList.isEmpty()
    }
    val isLoadingNextPage = remember(paginatedListSource.loadingState.value.isLoadingNextPage) {
        paginatedListSource.loadingState.value.isLoadingNextPage
    }
    if (showNetworkError.not()) {

        ComposeSwipeRefresh(
            false,
            onRefresh,
            swipeEnabled
        ) {
            PaginatedLazyList(
                modifier = lazyListModifier,
                contentPadding = contentPadding,
                state = state,
                isVertical = isVertical,
                spacingBetweenItems = spacingBetweenItems,
                flingBehavior = flingBehavior
            ) {

                itemsBeforeList?.invoke(this)

                if (showPlaceHolder) {
                    item {
                        emptyListPlaceholder()
                    }
                } else {
                    currentList.forEachIndexed { index, item ->
                        if (item is StickyModel) {
                            stickyHeader {
                                listItem.invoke(this, index, item)
                            }
                        } else {
                            item(key = item.uniqueId) {
                                listItem.invoke(this, index, item)
                                paginatedListSource.checkPosition(index)
                            }
                        }
                    }
                    if (showRetryLastPage) {
                        item {
                            LocalMainComponent.PagingRetryButton {
                                paginatedListSource.retryLastPage()
                            }
                        }
                    }
                    if (isLoadingNextPage) {
                        item {
                            CircularIndicator(
                                isLoading = true, color = indicatorColor
                            )
                        }
                    }
                }
            }
        }
    } else {
        LocalMainComponent.NetworkError(networkErrorModifier, true) {
            paginatedListSource.retryLastPage()
        }
    }
}

@Composable
fun PaginatedLazyList(
    modifier: Modifier,
    isVertical: Boolean,
    contentPadding: PaddingValues = PaddingValues(),
    state: LazyListState,
    spacingBetweenItems: Dp,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    content: LazyListScope.() -> Unit
) {
    return if (isVertical) {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding,
            state = state,
            flingBehavior = flingBehavior,
            verticalArrangement = Arrangement.spacedBy(spacingBetweenItems),
        ) {
            content()
        }
    } else {
        LazyRow(
            modifier = modifier,
            contentPadding = contentPadding,
            state = state,
            flingBehavior = flingBehavior,
            horizontalArrangement = Arrangement.spacedBy(spacingBetweenItems),
        ) {
            content()
        }
    }
}