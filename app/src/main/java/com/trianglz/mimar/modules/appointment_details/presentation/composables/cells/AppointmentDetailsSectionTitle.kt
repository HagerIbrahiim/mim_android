package com.trianglz.mimar.modules.appointment_details.presentation.composables.cells

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer

@Composable
fun AppointmentDetailsSectionTitle(
    title: Int,
    isLoading: () -> Boolean,
) {

    val titleBottomHeight: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium * 2
        }
    }

    val titleTopHeight: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium * 3 + 4.dp
        }
    }

    Column {

        Spacer(modifier = Modifier.height(titleTopHeight()))

        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
                .shimmer(isLoading()),
            fontWeight = FontWeight.W600
        )
        Spacer(modifier = Modifier.height(titleBottomHeight()))
    }

}