package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.ui.theme.dividerColor
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXLarge

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.dividerColor,
    thickness: Dp = MaterialTheme.dimens.borderSmall,
    padding: PaddingValues = PaddingValues(top = MaterialTheme.dimens.spaceBetweenItemsXXLarge)
) {
    Divider(
        color = color,
        modifier = modifier.padding(padding),
        thickness = thickness
    )
}