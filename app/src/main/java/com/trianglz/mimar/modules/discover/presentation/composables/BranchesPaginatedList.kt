package com.trianglz.mimar.modules.discover.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.modules.branches.presentation.composables.BranchItem
import com.trianglz.mimar.modules.branches.presentation.composables.NoAvailableBranchesDescription
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.common.presentation.pagination.ComposePaginatedList
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXLarge

@Composable
fun BranchesPaginatedList(
    source: () -> ComposePaginatedListDataSource<BranchUIModel>,
    isSwipeEnabled: () -> Boolean,
    locationAvailable: () -> Boolean,
    onRequestLocationClicked: () -> Unit,
    onChangeLocationClicked: () -> Unit,
    onRefresh: () -> Unit,

    ) {


    val state = rememberLazyListState()

    LaunchedEffect(key1 = source().loadingState.value) {
        if (source().loadingState.value.isLoading) {
            state.scrollToItem(0)
        }
    }

    ComposePaginatedList(
        swipeEnabled = isSwipeEnabled(),
        state = state,
        onRefresh = onRefresh,
        lazyListModifier = Modifier.fillMaxSize(),
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
                    animationFile =  R.raw.discover,
                    titleFirstText = { R.string.no_available },
                    titleSecondText = { R.string.providers },
                    titleDescSpacer = MaterialTheme.dimens.spaceBetweenItemsMedium,
                    imageContentSpacerHeight = MaterialTheme.dimens.spaceBetweenItemsXXLarge,
                    customDescription = {
                        if (locationAvailable().not()) {
                            NoAvailableBranchesDescription(
                                modifier = Modifier,
                                textAlign = TextAlign.Center,
                                locationAccessRequired = { true },
                                onClick = onRequestLocationClicked
                            )
                        } else {
                            NoAvailableBranchesDescription(
                                modifier = Modifier,
                                textAlign = TextAlign.Center,
                                locationAccessRequired = { false },
                                onClick = onChangeLocationClicked
                            )
                        }
                    },
                    animHeight = null,
                    animModifier =  Modifier.weight(1F)
                )

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