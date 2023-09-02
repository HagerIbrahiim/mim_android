package com.trianglz.mimar.modules.favourites.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.pagination.ComposePaginatedList
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.modules.branches.presentation.composables.BranchItem
import com.trianglz.mimar.modules.branches.presentation.composables.NoAvailableBranchesDescription
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel

@Composable
fun FavouritesPaginatedList(
    modifier: Modifier,
    source: () -> ComposePaginatedListDataSource<BranchUIModel>,
    onDiscoverBranchesClicked: () -> Unit,
    onRefresh: () -> Unit,

    ) {


    val state = rememberLazyListState()

    LaunchedEffect(key1 = source().loadingState.value) {
        if (source().loadingState.value.isLoading) {
            state.scrollToItem(0)
        }
    }


    ComposePaginatedList(
        state = state,
        onRefresh = onRefresh,
        lazyListModifier = Modifier
            .fillMaxSize()
            .then(modifier),
        contentPadding = PaddingValues(MaterialTheme.dimens.screenGuideDefault),
        paginatedListSource = source(),
        spacingBetweenItems = MaterialTheme.dimens.spaceBetweenItemsMedium,
        listItem = { _, item ->
            BranchItem(item = { item })

        },
        emptyListPlaceholder = {

            Column(
                modifier = Modifier.fillParentMaxSize()
            ) {
                MimarPlaceholder(
                    modifier = {
                        Modifier
                            .fillParentMaxWidth(1f)
                            .fillParentMaxHeight(1f)
                    },
                    animationFile =  R.raw.discover ,
                    titleFirstText = { R.string.no_available_favourite },
                    titleSecondText = { R.string.favorite_providers },
                    customDescription = {
                        NoAvailableBranchesDescription(
                            modifier = Modifier,
                            textAlign = TextAlign.Center,
                            locationAccessRequired = { false },
                            locationGrantedDescription = { R.string.to_add_favorite_branches },
                            actionText = { R.string.discover_branches},
                            onClick = onDiscoverBranchesClicked
                        )

                    }
                ) {

                }

                Spacer(
                    modifier = Modifier.height(
                        MaterialTheme.dimens.buttonHeight
                                + MaterialTheme.dimens.screenGuideDefault
                    )
                )
            }


        },
    )
}