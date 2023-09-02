package com.trianglz.mimar.modules.authentication.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.DeepPeach
import com.trianglz.mimar.common.presentation.ui.theme.xxSmall

@Composable
fun LanguageButtonWithArrow(
    currentLocale: @Composable () -> String,
    imageTintColor: @Composable () -> Color = { MaterialTheme.colors.surface },
    onLangButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.xxSmall)
            .clickable(onClick = onLangButtonClicked),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = currentLocale(),
            style = MaterialTheme.typography.body2.copy(
                fontWeight = FontWeight.W400,
                color = MaterialTheme.colors.surface
            ),
            modifier = Modifier
                .clip(MaterialTheme.shapes.xxSmall)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondary,
                            DeepPeach,
                        )
                    )
                )
                .padding(
                    horizontal = MaterialTheme.dimens.innerPaddingXSmall / 2, vertical =
                    MaterialTheme.dimens.innerPaddingMedium / 2
                )
        )

        Spacer(modifier = Modifier.padding(start = MaterialTheme.dimens.spaceBetweenItemsSmall))

        ImageFromRes(
            imageId = R.drawable.ic_arrow_down,
            modifier = Modifier
                .padding(end = MaterialTheme.dimens.innerPaddingXSmall)
                .clip(MaterialTheme.shapes.xxSmall),
            tintColor = imageTintColor()
        )
    }
}