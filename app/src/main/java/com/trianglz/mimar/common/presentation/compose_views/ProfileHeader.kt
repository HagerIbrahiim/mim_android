package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.extensions.ifTrue
import com.trianglz.mimar.common.presentation.models.ProfileHeaderInfoModel
import com.trianglz.mimar.common.presentation.ui.theme.personImageLarge
import com.trianglz.mimar.common.presentation.ui.theme.topRoundedCornerShapeLarge
import com.trianglz.mimar.modules.branch_reviews.presentation.composables.ReviewBar

@Composable
fun ProfileHeader(
    data: ProfileHeaderInfoModel,
) {

    val verticalSpacing = MaterialTheme.dimens.spaceBetweenItemsMedium

    val profileImagePadding: @Composable () -> Dp = remember {
        {
            MaterialTheme.dimens.innerPaddingXSmall / 2
        }
    }
    val ratingVisibility = remember(data.rating, data.showShimmer) {
        if (data.rating == null && !data.showShimmer) Visibility.Gone else Visibility.Visible
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary),
    ) {
        val startGuidLine = createGuidelineFromStart(MaterialTheme.dimens.screenGuideMedium)
        val endGuidLine = createGuidelineFromEnd(MaterialTheme.dimens.screenGuideMedium)

        val (backGroundImage, profileImage, userName, rating, curvedBackground) = createRefs()

        Box(
            modifier = Modifier
                .height(50.dp)
                .clip(MaterialTheme.shapes.topRoundedCornerShapeLarge)
                .background(MaterialTheme.colors.background)
                .constrainAs(curvedBackground) {
                    width = Dimension.matchParent
                    bottom.linkTo(parent.bottom)
                },
        )

        Box(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colors.surface)
                .constrainAs(backGroundImage) {
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                    top.linkTo(parent.top, 48.dp)
                    bottom.linkTo(rating.bottom)
                },
        )



        Box(
            modifier = Modifier
                .clip(CircleShape)
                .shimmer(data.showShimmer)
                .background(MaterialTheme.colors.background)
                .padding(profileImagePadding())
                .constrainAs(profileImage) {
                    start.linkTo(backGroundImage.start)
                    end.linkTo(backGroundImage.end)
                },
            contentAlignment = Alignment.Center,
        ) {
            ImageItem(
                image = data.image,
                modifier = Modifier
                    .size(MaterialTheme.dimens.personImageLarge)
                    .clip(CircleShape),
                animation = CrossfadePlugin(),
                placeholder = R.drawable.ic_profile_placeholder
            )
        }

        Text(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.spaceBetweenItemsLarge)
                .ifTrue (data.rating == null && !data.showShimmer){
                    Modifier.padding(bottom = MaterialTheme.dimens.spaceBetweenItemsLarge)
                }
                .shimmer(data.showShimmer)
                .constrainAs(userName) {
                    top.linkTo(profileImage.bottom, verticalSpacing)
                    width = Dimension.fillToConstraints
                    linkTo(startGuidLine, endGuidLine)
                },
            text = data.name,
            style = MaterialTheme.typography.subtitle1.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )


        ReviewBar(
            modifier = Modifier
                .wrapContentWidth()
                .padding(bottom = MaterialTheme.dimens.spaceBetweenItemsLarge)
                .constrainAs(rating) {
                    top.linkTo(userName.bottom, verticalSpacing)
                    width = Dimension.fillToConstraints
                    linkTo(startGuidLine, endGuidLine)
                    visibility = ratingVisibility
                },
            rating = { data.rating ?: 0 },
            spacingBetweenStars = MaterialTheme.dimens.spaceBetweenItemsMedium,
            showShimmer = { data.showShimmer }
        )
    }


}