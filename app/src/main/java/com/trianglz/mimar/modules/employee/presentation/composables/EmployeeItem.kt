package com.trianglz.mimar.modules.employee.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ImageItem
import com.trianglz.mimar.common.presentation.compose_views.VerticalDivider
import com.trianglz.mimar.common.presentation.ui.theme.personImageSize
import com.trianglz.mimar.modules.branch_reviews.presentation.composables.ReviewBar
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel


@Composable
fun EmployeeItem(data: () -> EmployeeUIModel,
                 showShimmer: () -> Boolean,
                 modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.innerPaddingLarge)
            .clickable(enabled = !showShimmer(), onClick = {
                data().id?.let { data().onClick(it) }
            }),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.innerPaddingLarge)
    ) {

        ImageItem(
            image = data().image,
            modifier = Modifier
                .clip(CircleShape)
                .size(MaterialTheme.dimens.personImageSize)
                .shimmer(showShimmer()),
            placeholder = R.drawable.ic_anyone
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.dimens.spaceBetweenItemsSmall,
                Alignment.CenterVertically
            )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer(showShimmer()),
                text = data().userName,
                style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.W600
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start

            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.innerPaddingXLarge),
                verticalAlignment = Alignment.CenterVertically,

            ) {
                data().rating?.let {
                    ReviewBar(modifier = Modifier,rating = { it.toInt() }, showShimmer,
                        MaterialTheme.dimens.spaceBetweenItemsSmall, 14.dp)
                    VerticalDivider()
                }
                data().offeredLocation?.let {
                    EmployeeWorkPlace({ it }, showShimmer)
                }
            }
        }
    }
}
