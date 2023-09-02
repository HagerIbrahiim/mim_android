package com.trianglz.mimar.modules.branch_reviews.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.pagination.ComposePaginatedList
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.HorizontalDivider
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.ui.theme.Iron
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXLarge
import com.trianglz.mimar.modules.branch_reviews.presentation.source.BranchReviewsSource
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun BranchReviewsScreenContent(
    navigator: DestinationsNavigator?,
    userModeHandler: UserModeHandler,
    source: BranchReviewsSource,
    branchName: () -> String,
    rating: () -> String,
    numOfReviews: () -> String,
    isRefreshing: () -> Boolean,
    onSwipeToRefresh: () -> Unit,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .statusBarsPadding()
            .calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight)
    ) {
        HeaderShadow(dividerColor = Iron) { _ ->
            ScreenHeader(
                navigator = navigator,
                userModeHandler = userModeHandler,
                header = { StringWrapper(stringMessage = branchName()) },
                modifier = Modifier.fillMaxWidth(),
                isAddBackButton = { true },
                isAddPadding = { false },
                showCartIcon = { true },
                showNotificationIcon = { true },
                showShimmer = isRefreshing,
                notificationsCount = notificationsCount,
                cartCount = cartCount
            )
        }

        ComposePaginatedList(
            paginatedListSource = source,
            onRefresh = onSwipeToRefresh,
            lazyListModifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = MaterialTheme.dimens.screenGuideDefault,
                end = MaterialTheme.dimens.screenGuideDefault,
                bottom = MaterialTheme.dimens.screenGuideDefault
            ),
            spacingBetweenItems = MaterialTheme.dimens.spaceBetweenItemsXXLarge,
            itemsBeforeList = {
                item {
                    Spacer(modifier = Modifier.size(MaterialTheme.dimens.spaceBetweenItemsXXLarge))
                    TotalReviewsItem(
                        rating = { rating() },
                        reviewsCount = { numOfReviews() },
                        isRefreshing = isRefreshing
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.customer_reviews),
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .shimmer(isLoading = isRefreshing(), shimmerWidth = 0.5f)
                    )
                }
            },
            listItem = { index, item ->
                val isLastReview by remember(source.dataList.value) {
                    derivedStateOf {
                        source.dataList.value.getList()?.size?.minus(1) == index
                    }
                }
                ReviewItem { item }
                if (!isLastReview) {
                    HorizontalDivider(padding = PaddingValues(0.dp))
                }
            },
            emptyListPlaceholder = {

            }
        )
    }
}
