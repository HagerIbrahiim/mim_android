package com.trianglz.mimar.modules.branch_details.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R

@Composable
fun ServicesTitleRow(
    isRefreshing: @Composable () -> Boolean,
    showFilterIcon:  () -> Boolean,
    hasFilterData:  () -> Boolean,
    onFilterIconClicked: () -> Unit,
    ) {

    val filterTintColor : @Composable () -> Color = remember(hasFilterData()) {
        {
            if (hasFilterData()) MaterialTheme.colors.secondary else MaterialTheme.colors.primary
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.dimens.screenGuideDefault
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(id = R.string.services),
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1F)
                .padding(
                    end = MaterialTheme.dimens.spaceBetweenItemsSmall
                )
                .shimmer(isLoading = isRefreshing()),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.W600
        )

        if(showFilterIcon()) {
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onFilterIconClicked,
                enabled = !isRefreshing()
            ) {
                ImageFromRes(
                    imageId = R.drawable.ic_filter_icon,
                    modifier = Modifier
                        .size(20.dp)
                        .padding()
                        .shimmer(isRefreshing()),
                    tintColor = filterTintColor(),
                )
            }
        }
    }

}