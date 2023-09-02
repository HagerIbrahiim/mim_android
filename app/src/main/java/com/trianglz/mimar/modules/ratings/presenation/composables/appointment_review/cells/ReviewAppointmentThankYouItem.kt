package com.trianglz.mimar.modules.ratings.presenation.composables.appointment_review.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.Typography

@Composable
fun ReviewAppointmentThankYouItem() {

    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.surface),
    horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.thank_you_using_mimar),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            style = Typography.subtitle1.copy(
                fontWeight = FontWeight.W700
            )
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))

        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.how_was_your_appointment),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            style = Typography.subtitle1.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.W400
            )
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsSmall))
    }
}