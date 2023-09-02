package com.trianglz.mimar.modules.branch_reviews.presentation.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.ratingIconSize

@Composable
fun ReviewBar(
    modifier: Modifier = Modifier,
    rating: () -> Int,
    showShimmer: () -> Boolean,
    spacingBetweenStars: Dp = MaterialTheme.dimens.innerPaddingSmall,
    ratingIconSize: Dp = MaterialTheme.dimens.ratingIconSize
) {


    Row(
        modifier = Modifier.wrapContentWidth().then(modifier),
        horizontalArrangement = Arrangement.spacedBy(spacingBetweenStars)
    ) {

        repeat(5) {
            val starIcon = remember(rating()) {
                if (rating() >= it.plus(1))
                    R.drawable.ic_rating
                else
                    R.drawable.star_silver
            }

            RatingIcon(
                showShimmer = showShimmer,
                starIcon = { starIcon },
                ratingIconSize = ratingIconSize
            )
        }
    }

}


@Preview
@Composable
private fun ReviewRatingItemPreview() {
    ReviewBar(rating = { 4 }, showShimmer = { false }, modifier = Modifier)
}
