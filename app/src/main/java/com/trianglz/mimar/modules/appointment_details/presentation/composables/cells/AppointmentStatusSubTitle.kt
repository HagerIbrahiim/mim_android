package com.trianglz.mimar.modules.appointment_details.presentation.composables.cells

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer

@Composable
fun AppointmentStatusSubTitle(text: () -> Int?, isLoading: () -> Boolean) {

    val statusTopSpacing: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium * 3
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(statusTopSpacing()))
        Text(
            text = text()?.let { stringResource(id = it) } ?: "",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.secondary,
                fontSize = 16.sp
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .shimmer(isLoading(), .4F),
            fontWeight = FontWeight.W700
        )
    }

}