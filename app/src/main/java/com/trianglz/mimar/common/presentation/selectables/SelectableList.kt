package com.trianglz.mimar.common.presentation.selectables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.selectables.model.SelectableUIModel

@Composable
fun SelectableList(
    showLoading: @Composable () -> Boolean,
    list: () -> List<SelectableUIModel>,
    selectedFilterIdByDefault: ()-> Int,
    state: LazyListState = rememberLazyListState(),
    onItemClicked: (Int) -> Unit
    ) {


    LaunchedEffect(key1 = selectedFilterIdByDefault()) {
        if ( selectedFilterIdByDefault() > 0) {
            val index = list().indexOfFirst { it.uniqueId == selectedFilterIdByDefault() }
            if(index > 0) {
                state.animateScrollToItem(index,-30)
            }
        }
    }

    LazyRow(
        state = state,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsSmall),
        contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.screenGuideDefault),
    ) {
        items(items= list(), key = { it.uniqueId }) {
            SelectableItem(
                item = { it }, onItemClicked
            )
        }
    }


}
