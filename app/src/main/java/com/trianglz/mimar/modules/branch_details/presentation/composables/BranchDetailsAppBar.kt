package com.trianglz.mimar.modules.branch_details.presentation.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ImageItem
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderType
import com.trianglz.mimar.common.presentation.ui.theme.Typography
import com.trianglz.mimar.common.presentation.ui.theme.xSmall
import com.trianglz.mimar.modules.branches.presentation.model.BranchDetailsUIModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BranchDetailsAppBar(
    showLoading: @Composable () -> Boolean,
    onAllInfoClicked: () -> Unit,
    onAllReviewsClicked: () -> Unit,
    containerModifier: () -> Modifier,
    branch: () -> BranchDetailsUIModel
) {

    val context = LocalContext.current


    val reviews: @Composable () -> AnnotatedString = remember(branch().reviewsCount,branch().rating) {
        {
            if (branch().reviewsCount == 0) {
                buildAnnotatedString {
                    append(stringResource(id = R.string.no_reviews_yet))
                }
            } else {
                buildAnnotatedString {
                    append(branch().rating.toString())
                    append(" ")
                    withStyle(style = SpanStyle(MaterialTheme.colors.onBackground)) {
                        val review = "${branch().reviewsCount} ${pluralStringResource(
                            id = R.plurals.no_of_reviews,branch().reviewsCount)}"
                        append("($review)")
                    }
                }
            }
        }

    }

    val favoriteIconTintColor: @Composable () -> Color = remember(branch().isFavorite) {
        {
            if (branch().isFavorite) {
                MaterialTheme.colors.primary
            } else {
                Color.Unspecified
            }
        }
    }

    val isFavouriteIcon = remember(branch().isFavorite) {
        if (branch().isFavorite) R.drawable.ic_favourites
        else R.drawable.ic_heart
    }

    val ratingIcon: @Composable () -> Int = remember(branch().reviewsCount) {
        {
            if (branch().reviewsCount == 0) {
                R.drawable.ic_no_rating
            } else {
                R.drawable.ic_rating
            }
        }
    }
    val showMale = remember(branch().servicedGenderType) {
        branch().servicedGenderType == ServicedGenderType.Male
                || branch().servicedGenderType == ServicedGenderType.Both

    }

    val showFemale = remember(branch().servicedGenderType) {
        branch().servicedGenderType == ServicedGenderType.Female
                || branch().servicedGenderType == ServicedGenderType.Both
    }

    val showMaleAndFemale = remember(showFemale, showMale) {
        showFemale && showMale
    }

    val gradientHeight: @Composable () -> Dp = remember {
        {
            WindowInsets.statusBars
                .asPaddingValues()
                .calculateTopPadding() + MaterialTheme.dimens.screenGuideDefault / 2
        }
    }
    val defaultGuide: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.screenGuideDefault }
    }
    val spaceBetweenItems: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.spaceBetweenItemsXSmall }
    }

    val spaceBetweenItemsTriple: @Composable () -> Dp = remember {
        { spaceBetweenItems() * 3 }
    }

    val defaultGuideHalf: @Composable () -> Dp = remember {
        { defaultGuide() / 2 }
    }

    val spaceBetweenItemsMedium: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.spaceBetweenItemsMedium }
    }



    val reviewsVerticalPadding : @Composable () -> Dp= remember(branch().reviewsCount) {
        {
            if (branch().reviewsCount == 0) MaterialTheme.dimens.innerPaddingXSmall else 0.dp
        }
    }

    val locationText = remember(branch().offeredLocation) {
        val branchOfferedLocationName =  branch().offeredLocation.name
        if (branchOfferedLocationName.getString(context).isNotEmpty())
            branchOfferedLocationName else
            StringWrapper(R.string.no_provided_location)
    }


    val branchImage = remember(branch().image,branch().serviceProviderLogo ) {
        branch().image.ifEmpty { branch().serviceProviderLogo }
    }

    val showHomeServices = remember(branch().offeredLocation) {
        branch().offeredLocation is OfferedLocationType.Both
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(containerModifier())
            .padding(bottom = MaterialTheme.dimens.spaceBetweenItemsMedium)
    ) {


        Box(modifier = Modifier.fillMaxWidth()) {

            ImageItem(
                image = branchImage,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(300.dp)
                    .clip(MaterialTheme.shapes.small)
                    .shimmer(showLoading()),
            )


            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(
                        gradientHeight()
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colors.primary.copy(.3f),
                                MaterialTheme.colors.primary.copy(0.2f),
                                MaterialTheme.colors.primary.copy(0.08f),
                            )
                        )
                    )
            )


            Box(
                modifier = Modifier
                    .padding(MaterialTheme.dimens.screenGuideDefault)
                    .align(Alignment.BottomEnd)
            ) {

                IconButton(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.surface.copy(alpha = .8F))
                        .padding(all = MaterialTheme.dimens.innerPaddingXSmall),
                    onClick = {
                        branch().onFavoriteClick(branch())
                    },
                    enabled = !showLoading(),
                ) {
                    Icon(
                        modifier = Modifier.shimmer(showLoading()),
                        imageVector = ImageVector.vectorResource(id = isFavouriteIcon),
                        contentDescription = null,
                        tint = favoriteIconTintColor(),
                    )
                }

            }
        }



        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = spaceBetweenItemsTriple(),
                    start = defaultGuide(),
                    end = defaultGuide()
                )
                .shimmer(showLoading(), shimmerModifier = Modifier.width(100.dp)),
            text = branch().name,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            style = MaterialTheme.typography.subtitle2.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            ),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = spaceBetweenItemsMedium(),
                    start = defaultGuide(),
                    end = defaultGuideHalf()
                )
        ) {
            TextStartsWithIcon(
                drawableRes = branch().offeredLocation.icon,
                iconTint = MaterialTheme.colors.secondary,
                data = locationText.getString(context),
                modifier = Modifier
                    .padding(end = defaultGuide())
                    .shimmer(showLoading())
                    .weight(1F)
            )

            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.xSmall)
                    .clickWithThrottle(enabled = !showLoading(), onClick = onAllInfoClicked)
                    .padding(all = MaterialTheme.dimens.innerPaddingXSmall)
                    .shimmer(showLoading()),
                text = stringResource(id = R.string.all_info),
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Typography.body2.copy(
                    color = MaterialTheme.colors.secondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W500
                )
            )
        }

        if(showHomeServices || showLoading()) {
            TextStartsWithIcon(
                drawableRes = R.drawable.ic_home_location,
                iconTint = MaterialTheme.colors.secondary,
                data = stringResource(id = R.string.home_services_available),
                modifier = Modifier
                    .padding(bottom = spaceBetweenItemsMedium(),)
                    .padding(horizontal = defaultGuide())
                    .shimmer(showLoading())
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = defaultGuide())
        ) {
            if (showFemale || showMaleAndFemale || showLoading()) {
                TextStartsWithIcon(
                    drawableRes = R.drawable.female_icon,
                    iconTint = MaterialTheme.colors.secondary,
                    data = stringResource(id = R.string.females),
                    modifier = Modifier.shimmer(showLoading())

                )
            }


            if (showMaleAndFemale || showLoading()) {
                Spacer(modifier = Modifier.padding(start = MaterialTheme.dimens.innerPaddingLarge))

                Text(
                    text = stringResource(id = R.string.and_symbol),
                    style = MaterialTheme.typography.caption.copy(
                        color = MaterialTheme.colors.primary
                    ),
                    modifier = Modifier
                        .shimmer(showLoading())
                )

                Spacer(modifier = Modifier.padding(start = MaterialTheme.dimens.innerPaddingLarge))
            }


            if (showMale || showMaleAndFemale || showLoading()) {
                TextStartsWithIcon(
                    drawableRes = R.drawable.male_icon,
                    iconTint = MaterialTheme.colors.secondary,
                    data = stringResource(id = R.string.males),
                    modifier = Modifier.shimmer(showLoading())
                )
            }

        }



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = defaultGuide(), end = defaultGuideHalf()),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextStartsWithIcon(
                modifier = Modifier
                    .padding(vertical = reviewsVerticalPadding())
                    .shimmer(showLoading())
                    .weight(1F),
                drawableRes = ratingIcon(),
                data = reviews.invoke(),
            )

            if (branch().reviewsCount > 0 || showLoading()) {
                Text(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.xSmall)
                        .clickWithThrottle(enabled = !showLoading(), onClick = onAllReviewsClicked)
                        .padding(all = MaterialTheme.dimens.innerPaddingXSmall)
                        .shimmer(showLoading()),
                    text = stringResource(id = R.string.all_reviews),
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = Typography.body2.copy(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.W500
                    )
                )
            }

        }


    }

}