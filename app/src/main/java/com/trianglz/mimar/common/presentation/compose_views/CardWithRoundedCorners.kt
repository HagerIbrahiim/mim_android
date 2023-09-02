/*
 * *
 *  * Created by Ahmed Awad on 1/3/23, 1:06 PM
 *
 */

package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.mimar.common.presentation.extensions.ifNotNull
import com.trianglz.mimar.common.presentation.ui.theme.Shapes


@Composable
fun CardWithRoundedTopCorners(
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colors.onSurface,
    backgroundColor: Color = MaterialTheme.colors.background,
    borderThickness: Dp = 1.dp,
    clickable: Boolean?,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val onClickAction = remember {
        {
            onClick?.invoke()
        }
    }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(
                    borderThickness,
                    borderColor
                ),
                shape = Shapes.large
            )
            .clip(
                shape = RoundedCornerShape(
                    topEnd = MaterialTheme.dimens.screenGuideLarge,
                    topStart = MaterialTheme.dimens.screenGuideLarge
                )
            )
            .ifNotNull(clickable) {
                Modifier.clickWithThrottle {
                    onClickAction.invoke()
                }
            },
        color = backgroundColor
    ) {
        content()
    }
}
