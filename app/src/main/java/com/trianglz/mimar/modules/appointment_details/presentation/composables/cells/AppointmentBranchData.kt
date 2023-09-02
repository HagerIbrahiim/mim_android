package com.trianglz.mimar.modules.appointment_details.presentation.composables.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ImageItem
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.common.presentation.ui.theme.appointmentBranchItemHeight
import com.trianglz.mimar.modules.branches.domain.model.BranchStatusType
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AppointmentBranchData(data: () -> BranchUIModel) {

    val branch = remember(data().showShimmer) {
        data()
    }
    val reviews: @Composable () -> AnnotatedString = remember(branch.reviewsCount,branch.rating) {
        {
            if (branch.reviewsCount == 0) {
                buildAnnotatedString {
                    append(stringResource(id = R.string.no_reviews_yet))
                }
            } else {
                buildAnnotatedString {
                    append(branch.rating.toString())
                    append(" ")
                    withStyle(style = SpanStyle(MaterialTheme.colors.onBackground)) {
                        val review = "${branch.reviewsCount} ${
                            pluralStringResource(
                            id = R.plurals.no_of_reviews,branch.reviewsCount)
                        }"
                        append("($review)")
                    }
                }
            }
        }

    }

    val ratingIcon: @Composable () -> Int = remember(branch.reviewsCount) {
        {
            if (branch.reviewsCount == 0) {
                R.drawable.ic_no_rating
            } else {
                R.drawable.ic_rating
            }
        }
    }

    val branchImage = remember(branch.image,branch.serviceProviderLogo ) {
        branch.image.ifEmpty { branch.serviceProviderLogo }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
        .clip(MaterialTheme.shapes.small)
        .clickable(
            onClick = {
                branch.onClick(branch.id)
            })
    ) {
        ImageItem(
            image = branchImage,
            modifier = Modifier
                .requiredHeight(MaterialTheme.dimens.appointmentBranchItemHeight)
                .clip(MaterialTheme.shapes.small)
                .shimmer(data().showShimmer),
            placeholder = R.drawable.ic_location_required
        )

        Spacer(modifier = Modifier
            .matchParentSize()
            .background(MaterialTheme.colors.primary.copy(alpha = .85F))
            .clip(MaterialTheme.shapes.small))

        Column(modifier = Modifier.matchParentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(id = R.string.provided_by),
                style = MaterialTheme.typography.caption.copy(
                    color = MaterialTheme.colors.surface,
                    fontWeight = FontWeight.W500
                ),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.shimmer(data().showShimmer),
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXSmall))

            Text(
                text = data().name,
                style = MaterialTheme.typography.subtitle2.copy(
                    color = MaterialTheme.colors.surface,
                    fontWeight = FontWeight.W700,
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.surface,
                modifier = Modifier.shimmer(data().showShimmer).padding(
                    horizontal = MaterialTheme.dimens.spaceBetweenItemsLarge
                ),
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXSmall))

            TextStartsWithIcon(
                modifier = Modifier
                    .shimmer(data().showShimmer,),
                drawableRes = data().offeredLocation.icon,
                data = data().offeredLocation.name.getString(LocalContext.current),
                textColor  = MaterialTheme.colors.surface,
                iconTint = MaterialTheme.colors.surface
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXSmall))

            TextStartsWithIcon(
                modifier = Modifier
                    .shimmer(branch.showShimmer),
                textColor  = MaterialTheme.colors.surface,
                drawableRes = ratingIcon(),
                data = reviews.invoke(),
            )

        }

    }
}