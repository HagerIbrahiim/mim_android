package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.composables.AutoSizedText
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeXSmall


@Composable
fun CircleBadgeItem(modifier: Modifier = Modifier, borderColor: Color = MaterialTheme.colors.surface, backgroundColor: Color = MaterialTheme.colors.secondary, number: Int, hideNumber: Boolean = false) {

    val badgeNumber: @Composable () -> String = remember(number) {
        {
            if (hideNumber) {
                ""
            } else if (number > 99) {
                stringResource(id = R.string.plus_99)
            } else {
                number.toString()
            }
        }
    }

    Box(
        modifier = Modifier
            .size(MaterialTheme.dimens.iconSizeXSmall)
//            .size(MaterialTheme.dimens.iconSizeXSmall)
            .background(borderColor, shape = CircleShape)
            .padding(MaterialTheme.dimens.borderMedium)
            .background(backgroundColor, shape = CircleShape)
            .then(modifier)
    ) {
        AutoSizedText(
            modifier = Modifier.align(Alignment.Center),
            text = badgeNumber(),
            textStyle = MaterialTheme.typography.overline.copy(
                color = MaterialTheme.colors.onSecondary,
                fontSize = 8.sp,
                fontWeight = FontWeight.W600
            ),
            maxLines = 1,
        )
    }
}