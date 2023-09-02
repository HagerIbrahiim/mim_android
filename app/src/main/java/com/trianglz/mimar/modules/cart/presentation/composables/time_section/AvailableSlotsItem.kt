package com.trianglz.mimar.modules.cart.presentation.composables.time_section

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.time.presentation.model.TimeUIModel


@Composable
fun AvailableSlotsItem(availableSlots: SnapshotStateList<TimeUIModel>) {

    val selectedIndex = remember(availableSlots.size) {
        var index = availableSlots.indexOfFirst { it.isSelected.value }
        if (index == -1 && availableSlots.size > 1) {
            index = 0
        }
        index
    }

    val state = rememberLazyListState()
    LaunchedEffect(key1 = selectedIndex) {
        if ( selectedIndex > -1) {
            state.animateScrollToItem(selectedIndex, -30)
        }
    }
    if (availableSlots.isEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.screenGuideDefault),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.dimens.spaceBetweenItemsXSmall,
                Alignment.CenterHorizontally
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_no_available),
                contentDescription = "ic_no_available",
                tint = MaterialTheme.colors.secondary
            )
            Text(
                text = stringResource(id = R.string.no_available_time_slots_on_this_day),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.secondary,
                )
            )
        }

    } else {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            state = state,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsSmall),
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.screenGuideDefault),
        ) {
            items(items = availableSlots, key = { it.uniqueId }) {
                AvailableTimeItem(
                    item = { it },
                )
            }
        }
    }
}
