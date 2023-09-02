package com.trianglz.mimar.modules.branches.presentation.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ImageItem
import com.trianglz.mimar.common.presentation.compose_views.TextStartsWithIcon
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BranchItem(modifier: Modifier = Modifier, item: () -> BranchUIModel, backgroundColor : @Composable () -> Color  = {
    MaterialTheme.colors.surface
}) {

    val branch = remember(item().isFavorite, item().showShimmer) {
        item()
    }

    val context = LocalContext.current


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
                        val review = "${branch.reviewsCount} ${pluralStringResource(
                            id = R.plurals.no_of_reviews,branch.reviewsCount)}"
                        append("($review)")
                    }
                }
            }
        }

    }

    val favoriteIcon: @Composable () -> Int = remember(branch.isFavorite) {
        {
            if (branch.isFavorite) {
                R.drawable.ic_heart_filled
            } else {
                R.drawable.ic_heart
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


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = backgroundColor(), shape = MaterialTheme.shapes.small)
            .clip(shape = MaterialTheme.shapes.small)
            .then(modifier)
            .clickable {
                branch.onClick(branch.uniqueId)
            },
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .height(IntrinsicSize.Min),
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(
                        top = MaterialTheme.dimens.innerPaddingLarge,
                        bottom = MaterialTheme.dimens.innerPaddingLarge,
                        start = MaterialTheme.dimens.innerPaddingLarge,
                        end = MaterialTheme.dimens.innerPaddingXSmall
                    ),
                verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shimmer(branch.showShimmer, shimmerModifier = Modifier.width(100.dp)),
                    text = branch.name,
                    textAlign = TextAlign.Start, overflow = TextOverflow.Ellipsis, maxLines = 2,
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onSurface,
                        fontWeight = FontWeight.W700
                    ),
                )
                Spacer(modifier = Modifier.weight(1f))
                branch.offeredLocation.name.getString(context).let {
                    if (it.isNotEmpty()) {
                        TextStartsWithIcon(
                            modifier = Modifier.shimmer(
                                branch.showShimmer,
                                shimmerModifier = Modifier.width(80.dp)
                            ),
                            drawableRes = branch.offeredLocation.icon,
                            iconTint = MaterialTheme.colors.secondary,
                            data = branch.offeredLocation.name.getString(context)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
//                    RatingBarStar(rating = branch.rating)
//                    //TODO Change text to string resource when project is created
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Text(
//                        modifier = Modifier.weight(1F),
//                        text = "${branch.reviewsCount} Reviews",
//                        textAlign = TextAlign.Start, overflow = TextOverflow.Ellipsis, maxLines = 1,
//                        style = Typography.subtitle1.copy(
//                            color = SecondaryTextColor,
//                            fontSize = 12.sp,
//                            fontWeight = FontWeight.W400
//                        )
//                    )
                    TextStartsWithIcon(
                        modifier = Modifier.shimmer(branch.showShimmer),
                        drawableRes = ratingIcon(),
                        data = reviews.invoke()
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f))

                }

            }
            Box(modifier = Modifier
                .width(114.dp)
                .height(125.dp)
            ){
                ImageItem(
                    image = branch.serviceProviderLogo, modifier = Modifier
//                    image = "https://static.vecteezy.com/packs/media/vectors/term-bg-1-3d6355ab.jpg", modifier = Modifier
                        .fillMaxSize()
                        .shimmer(branch.showShimmer)
                        .background(MaterialTheme.colors.onPrimary),
                )
                Box(modifier = Modifier
                    .padding(MaterialTheme.dimens.innerPaddingSmall)
                    .size(32.dp)
                    .background(Color.White.copy(0.8f), shape = CircleShape)
                    .clip(CircleShape)
                    .align(Alignment.TopEnd)
                    .clickWithThrottle { branch.onFavoriteClick(branch) },
                ){
                    Crossfade(
                        targetState = favoriteIcon(),
                        animationSpec = tween(1000),
                        modifier = Modifier.align(Alignment.Center)
                    ) { icon ->
                        Icon(
                            modifier = Modifier
                                .shimmer(branch.showShimmer)
                                .size(14.dp),
                            imageVector = ImageVector.vectorResource(id = icon),
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary,
                        )
                    }
                }

            }

        }
    }
}

@Preview
@Composable
fun BranchItemPreview() {
    MaterialTheme {
        BranchItem(
            item = {
                BranchUIModel(
                    name = "Alexandria",
                    image = "",
                    location = "Louran, Abdul-Salam Aref St",
                    reviewsCount = 9,
                    rating = 4f,
                    id = 1,
                    onFavoriteClick = {},
                    onClick = {}
                )
            }
        )
    }
}