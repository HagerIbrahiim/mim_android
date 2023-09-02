package com.trianglz.mimar.common.presentation.selectables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.getValue
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.common.presentation.selectables.model.SelectableUIModel
import com.trianglz.mimar.common.presentation.ui.theme.xSmall

@Composable
fun SelectableItem(item: () -> SelectableUIModel, onItemClicked: (Int) -> Unit) {

    val isChecked by remember(item().isChecked.value) {
        item().isChecked
    }

    val context = LocalContext.current

    val textColor: @Composable () -> Color = remember(isChecked) {
        {
            if (isChecked) {
                MaterialTheme.colors.surface
            } else {
                MaterialTheme.colors.primary
            }
        }
    }

    val backgroundColor  : @Composable () -> Color = remember(isChecked) {
        { if (isChecked) MaterialTheme.colors.secondary else MaterialTheme.colors.surface }
    }

    Text(
        text = item().title.getString(context),
        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W400, color = textColor()),
        modifier = Modifier
            .clip(MaterialTheme.shapes.xSmall)
            .background(backgroundColor())
            .clickable(
                enabled = !item().showShimmer,
                onClick = { onItemClicked(item().uniqueId) }
            )
            .padding(MaterialTheme.dimens.innerPaddingSmall)
            .shimmer(item().showShimmer, shimmerWidth = 0.3f),
    )
}