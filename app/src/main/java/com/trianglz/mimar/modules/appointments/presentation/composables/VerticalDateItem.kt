package com.trianglz.mimar.modules.appointments.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.common.presentation.ui.theme.verticalDateItemMinWidth
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentDate


@Composable
fun VerticalDateItem(date: () -> AppointmentDate?, showShimmer: () -> Boolean = {false}) {

    val spaceBetweenDate : @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsXSmall / 3
        }
    }

    Column(
        modifier = Modifier
            .widthIn(min = MaterialTheme.dimens.verticalDateItemMinWidth)
            .fillMaxHeight()
            .background(
                MaterialTheme.colors.secondary.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.small
            )
            .shimmer(showShimmer())
            .padding(vertical = MaterialTheme.dimens.innerPaddingDefault),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = date()?.month ?: "",
            textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1,
            style = MaterialTheme.typography.subtitle1.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.W300
            ),
        )
        Spacer(modifier = Modifier.height(spaceBetweenDate()))
        Text(
            text = date()?.day ?: "",
            textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1,
            style = MaterialTheme.typography.subtitle1.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = 22.sp,
                fontWeight = FontWeight.W700
            )
        )
        Spacer(modifier = Modifier.height(spaceBetweenDate()))
        Text(
            text = date()?.year ?: "",
            textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1,
            style = MaterialTheme.typography.subtitle1.copy(
                color = MaterialTheme.colors.onSurface,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300
            )
        )
    }
}
