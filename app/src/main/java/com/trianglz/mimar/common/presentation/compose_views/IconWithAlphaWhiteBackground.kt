package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.mimar.common.presentation.ui.theme.xSmall

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IconWithAlphaWhiteBackground(modifier: Modifier = Modifier,
                                 enabled: @Composable () -> Boolean = {true},
                                 icon: () -> Int,
                                 iconSize: () -> Dp = {32.dp}, onClick: () -> Unit) {
    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
        IconButton(
            modifier = Modifier
                .size(iconSize())
                .clip(MaterialTheme.shapes.xSmall)
                .background(MaterialTheme.colors.surface.copy(alpha = .8F))
                .then(modifier),
            onClick = onClick,
            enabled = enabled(),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon()),
                contentDescription = null,
                tint = MaterialTheme.colors.primary,
            )
        }
    }
}