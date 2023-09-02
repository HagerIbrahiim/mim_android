package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens

@Composable
fun ButtonGradientBackground(
    containerModifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    1f to Color.White.copy(alpha = 0.5f),
                    0f to Color.White,
                )
            )
            .then(containerModifier)
    ) {

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideDefault))

        content()

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideDefault))

    }
}