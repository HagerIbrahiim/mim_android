package com.trianglz.mimar.modules.branch_view_all.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.common.presentation.models.CoveredZonesUIModel

@Composable
fun SupportedAreaItem(data: ()-> CoveredZonesUIModel, showShimmer: () -> Boolean, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.fillMaxWidth().shimmer(showShimmer()),
        text = data().name,
        style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.W600
        ),
        maxLines = 2,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis
    )
}