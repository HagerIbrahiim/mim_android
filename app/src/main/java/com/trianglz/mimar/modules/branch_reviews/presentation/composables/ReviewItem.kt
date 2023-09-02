package com.trianglz.mimar.modules.branch_reviews.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.extensions.formatDate
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.domain.enum.SimpleDateFormatData.DayMonthYear
import com.trianglz.mimar.common.domain.enum.SimpleDateFormatData.DayMonthYearEn
import com.trianglz.mimar.common.presentation.compose_views.ImageItem
import com.trianglz.mimar.modules.branches.presentation.model.BranchReviewUIModel

@Composable
fun ReviewItem(data: () -> BranchReviewUIModel) {

    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.innerPaddingMedium)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.dimens.innerPaddingSmall
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImageItem(
                image = "",
                modifier = Modifier
                    .size(25.dp)
                    .clip(CircleShape)
                    .shimmer(data().showShimmer),
                placeholder = R.drawable.ic_profile_placeholder
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .shimmer(data().showShimmer, shimmerWidth = 0.5f),
                text = data().name ?: stringResource(id = R.string.anyone),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle2,
            )
            data().date?.let {
                Text(
                    modifier = Modifier
                        .shimmer(data().showShimmer, shimmerWidth = 0.5f)
                        .weight(1f),
                    text = it,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.caption.copy(
                        color = MaterialTheme.colors.onBackground,
                    ),
                )
            }

        }
        ReviewBar(rating = { data().rating }, showShimmer = { data().showShimmer })
        data().feedback?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(data().showShimmer),
                text = it,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary),
            )
        }
    }
}
