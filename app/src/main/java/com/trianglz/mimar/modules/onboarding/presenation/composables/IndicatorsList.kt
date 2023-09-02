package com.trianglz.mimar.modules.onboarding.presenation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens

@Composable
fun BoxScope.IndicatorsList(
    size: () -> Int,
    index: () -> Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsXSmall),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        repeat(size()) {
            Indicator { it == index() }
        }
    }
}


