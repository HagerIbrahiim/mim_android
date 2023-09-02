package com.trianglz.mimar.modules.appointment_details.presentation.composables.cells

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.HorizontalDivider
import com.trianglz.mimar.common.presentation.compose_views.MultiStyleText

@Composable
fun AppointmentThankYouMessage() {

    val dividerTopHeight: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium * 3 + 4.dp
        }
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        ImageFromRes(imageId = R.drawable.ic_success, modifier = Modifier)

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsLarge))

        Text(
            text = stringResource(id = R.string.thank_you),
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.W700
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.W700
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXLarge))

        MultiStyleText(
            firstText = stringResource(id = R.string.appointment),
            firstColor = MaterialTheme.colors.primary,
            secondText = stringResource(id = R.string.confirmed),
            secondColor = MaterialTheme.colors.secondary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.W700
            )
        )

        HorizontalDivider(
            padding = PaddingValues(
                start = MaterialTheme.dimens.screenGuideDefault,
                end = MaterialTheme.dimens.screenGuideDefault,
                top = dividerTopHeight()
            )
        )


    }
}