package com.trianglz.mimar.modules.appointment_details.presentation.composables.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.common.presentation.ui.theme.cardElevationMedium
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeXXXSmall


@Composable
fun AppointmentDetailsDataItem(
    title: Int,
    titleIcon: Int,
    primaryDescription: String,
    secondaryDescription: String? = null,
    isLoading: () -> Boolean,
    shape: Shape,
    modifier: Modifier = Modifier,
) {


    val textStartPadding: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.iconSizeXXXSmall + MaterialTheme.dimens.spaceBetweenItemsXSmall
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
            .shadow(
                elevation =  MaterialTheme.dimens.cardElevationMedium,
                shape = shape,
                spotColor = MaterialTheme.colors.primary.copy(alpha = .5F)
            )
            .clip(shape)
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = MaterialTheme.dimens.innerPaddingLarge)
            .padding(top = MaterialTheme.dimens.innerPaddingLarge)
            .then(modifier)

    ) {

        TextStartsWithIcon(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer(isLoading()),
            data = stringResource(id = title),
            drawableRes = titleIcon,
            iconTint = MaterialTheme.colors.secondary,
            textColor = MaterialTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXSmall))

        Text(
            text = primaryDescription,
            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.W500),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = textStartPadding())
                .shimmer(isLoading()),

        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXSmall))

        secondaryDescription?.let {
            Text(
                text = "($it)",
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.W300),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(start = textStartPadding())
                    .shimmer(isLoading()),
            )
        }

    }
}