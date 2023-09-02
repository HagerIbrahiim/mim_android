package com.trianglz.mimar.modules.onboarding.presenation.composables

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Indicator(isSelected: () -> Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected()) 20.dp else 6.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    val primaryColor: @Composable () -> Color = remember {
        {
            MaterialTheme.colors.primary
        }
    }

    val onBackground: @Composable () -> Color = remember {
        {
            MaterialTheme.colors.onBackground
        }
    }

    val gradient : @Composable () -> Brush  = remember(isSelected()) {
        {
            if (isSelected())
                Brush.horizontalGradient(
                    colors = listOf(
                        primaryColor(),
                        onBackground()
                    ),
                )
            else Brush.horizontalGradient(
                colors = listOf(
                    onBackground(),
                    onBackground(),
                ),
            )
        }
    }

    Box(
        modifier = Modifier
            .height(6.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(gradient())
    ) {

    }
}
