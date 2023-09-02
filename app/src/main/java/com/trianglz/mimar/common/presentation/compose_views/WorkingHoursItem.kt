package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer

@Composable
fun WorkingHoursItem(
    day: () -> String?,
    description: @Composable () -> String,
    showShimmer: () -> Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        day()?.let {
            Text(
                text = it,
                modifier = Modifier.padding(end = MaterialTheme.dimens.innerPaddingSmall).shimmer(showShimmer()),
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.W600
                ),
                maxLines = 2,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis

            )
        }

        Text(
            text = description(),
            modifier = Modifier.weight(1f).shimmer(showShimmer()),
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.W600
            ),
            maxLines = 2,
            textAlign = TextAlign.End,
            overflow = TextOverflow.Ellipsis
        )
    }
}