package com.trianglz.mimar.modules.ratings.presenation.composables.appointment_review.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.Typography
import com.trianglz.mimar.common.presentation.ui.theme.xSmall
import com.trianglz.mimar.modules.ratings.presenation.model.RatingAppointmentUIModel

@Composable
fun RateEmployeeItem(data: () -> RatingAppointmentUIModel) {

    val context = LocalContext.current

    val spacingBetweenContent: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.spaceBetweenItemsMedium + 4.dp
        }
    }

    Column(
        modifier = Modifier
            .padding(top = MaterialTheme.dimens.spaceBetweenItemsXLarge)
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
            .clip(MaterialTheme.shapes.xSmall)
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(vertical = MaterialTheme.dimens.innerPaddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            modifier = Modifier,
            text = StringWrapper{
                  this.getString(R.string.how_was_employee, data().name)
            }.getString(context),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = Typography.subtitle1.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W400
            )
        )

        Spacer(modifier = Modifier.height(spacingBetweenContent()))

        RatingBar(
            value = data().rating.value,
            config = RatingBarConfig()
                .style(RatingBarStyle.HighLighted),
            onValueChange = {
                data().rating.value = it
                data().onValueChange(it)
            },
            onRatingChanged = {}
        )


    }
}