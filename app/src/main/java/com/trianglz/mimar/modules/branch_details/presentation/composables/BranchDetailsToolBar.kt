package com.trianglz.mimar.modules.branch_details.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.CircleBadgeItem
import com.trianglz.mimar.common.presentation.compose_views.IconWithAlphaWhiteBackground
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXLarge

@Composable
fun BranchDetailsToolBar(
    modifier: Modifier,
    textModifier: Modifier,
    isLoading: @Composable () -> Boolean,
    branchName: () -> String,
    progress: () -> Float,
    onNotificationClicked: () -> Unit = {},
    onCartClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
    notificationsCount: State<Int>,
    cartCount: State<Int>,
) {

    val notificationsNumber by remember {
        notificationsCount
    }

    val cartServicesNumber by remember {
        cartCount
    }

    val showTitleShimmer: @Composable () -> Boolean = remember(progress()) {
        { isLoading() && progress() < .8F }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconWithAlphaWhiteBackground(
            icon = { R.drawable.ic_back },
            onClick = onBackClicked
        )

        Text(
            modifier = Modifier
                .padding(MaterialTheme.dimens.spaceBetweenItemsSmall)
                .weight(1F)
                .shimmer(showTitleShimmer())
                .then(textModifier),
            text = branchName(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle2
        )
        Box(modifier = Modifier) {
            IconWithAlphaWhiteBackground(
                icon = { R.drawable.notification_icon },
                onClick = onNotificationClicked
            )
            if (notificationsNumber > 0) {
                CircleBadgeItem(modifier = Modifier.align(Alignment.TopEnd), number = notificationsNumber, hideNumber = true)
            }
        }
        Spacer(
            modifier = Modifier.padding(
                start = MaterialTheme.dimens.spaceBetweenItemsXXLarge.minus(
                    MaterialTheme.dimens.innerPaddingXSmall
                )
            )
        )
        Box(modifier = Modifier) {
            IconWithAlphaWhiteBackground(
                icon = { R.drawable.cart_icon },
                onClick = onCartClicked
            )
            if (cartServicesNumber > 0) {
                CircleBadgeItem(modifier = Modifier.align(Alignment.TopEnd), number = cartServicesNumber)
            }
        }
    }
}

