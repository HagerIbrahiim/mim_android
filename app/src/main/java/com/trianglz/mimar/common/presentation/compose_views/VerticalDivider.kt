package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.ui.theme.dividerThickness

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    height: Dp = MaterialTheme.dimens.innerPaddingSmall,
    thickness: Dp = MaterialTheme.dimens.dividerThickness
) {
    Divider(
        color = MaterialTheme.colors.onBackground,
        modifier = modifier
            .fillMaxHeight()
            .width(thickness)
    )
}