package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.composables.CustomLottieAnimation
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.extensions.ifTrue
import com.trianglz.mimar.common.presentation.ui.theme.lottieHeight

@Composable
fun MimarLottieAnimation(
    lottieFile: Int,
    height: Dp? = MaterialTheme.dimens.lottieHeight,
    modifier: Modifier = Modifier
) {
    CustomLottieAnimation(
        lottieFile = lottieFile,
        speedValue = 2F,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .ifTrue(height != null) {
                height?.let { Modifier.requiredHeight(it) } ?: Modifier
            }
            .then(modifier)
    )
}