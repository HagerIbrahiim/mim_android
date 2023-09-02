package com.trianglz.mimar.common.presentation.compose_views

import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium
import com.trianglz.mimar.common.presentation.ui.theme.xSmall
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.core_compose.presentation.images.ImageFromRes

@Composable
fun ImageWithTitle(
    @StringRes titleRes: () -> Int,
    modifier: () -> Modifier = { Modifier },
    onClicked: () -> Unit,
    @DrawableRes iconRes: @Composable () -> Int,
    tintColor: Color? = null,
    iconModifier: Modifier = Modifier,
    iconSize: Dp = MaterialTheme.dimens.iconSizeMedium
) {

    val topPadding: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingLarge * 2
        }
    }

    val bottomPadding: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingXLarge.plus(2.dp)
        }
    }

    Column(
        modifier = modifier()
            .clip(MaterialTheme.shapes.xSmall)
            .background(MaterialTheme.colors.surface)
            .clickWithThrottle(onClick = onClicked)
            .padding(
                top = topPadding(),
                bottom = bottomPadding()
            ),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.spaceBetweenItemsLarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ImageFromRes(
            imageId = iconRes(),
            modifier = Modifier.size(iconSize).then(iconModifier),
            tintColor = tintColor
        )

        Text(
            text = stringResource(id = titleRes()),
            style = MaterialTheme.typography.caption.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface
            )
        )
    }
}