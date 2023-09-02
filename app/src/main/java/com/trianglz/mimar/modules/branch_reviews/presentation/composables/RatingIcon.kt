package com.trianglz.mimar.modules.branch_reviews.presentation.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.ratingIconSize



@Composable
fun RatingIcon(showShimmer: () -> Boolean,
               starIcon : () -> Int,
               ratingIconSize: Dp = MaterialTheme.dimens.ratingIconSize,) {
    ImageFromRes(
        imageId = starIcon(),
        modifier = Modifier
            .size(ratingIconSize)
            .shimmer(showShimmer()),
    )
}