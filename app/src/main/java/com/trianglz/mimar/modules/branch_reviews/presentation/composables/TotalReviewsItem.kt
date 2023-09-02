package com.trianglz.mimar.modules.branch_reviews.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.Rating

@Composable
fun TotalReviewsItem(
    modifier: Modifier = Modifier,
    rating: () -> String,
    reviewsCount: () -> String,
    isRefreshing: () -> Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.onPrimary)
            .padding(vertical = MaterialTheme.dimens.innerPaddingLarge)
            .height(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            MaterialTheme.dimens.innerPaddingLarge,
            Alignment.CenterHorizontally
        ),
    ) {
        ImageFromRes(
            imageId = R.drawable.ic_rating,
            modifier = Modifier
                .size(22.dp)
                .shimmer(isRefreshing()),
        )
        Text(
            modifier = Modifier.shimmer(isRefreshing()),
            text = rating(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.h4
        )
        Divider(
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = MaterialTheme.dimens.innerPaddingXSmall)
                .width(MaterialTheme.dimens.borderSmall)
                .shimmer(isRefreshing())
        )
        Text(
            modifier = Modifier.shimmer(isRefreshing()),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            text = buildAnnotatedString {
                append(stringResource(id = R.string.based_on))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(" (${reviewsCount()}) ")
                }
                append(stringResource(id = R.string.reviews))
            },
            style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Normal)
        )
    }
}

@Preview
@Composable
private fun TotalReviewsItemPreview() {
    MaterialTheme {
        TotalReviewsItem(rating = { "4.5" }, reviewsCount = { "46" }, isRefreshing = { false })
    }
}