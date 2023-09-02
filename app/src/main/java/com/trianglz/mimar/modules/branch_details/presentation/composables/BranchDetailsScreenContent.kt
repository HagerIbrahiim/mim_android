package com.trianglz.mimar.modules.branch_details.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.common.presentation.compose_views.CollapsingToolbarScaffold
import com.trianglz.mimar.common.presentation.compose_views.CollapsingToolbarScaffoldState
import com.trianglz.mimar.common.presentation.compose_views.ScrollStrategy
import com.trianglz.mimar.common.presentation.selectables.SelectableList
import com.trianglz.mimar.common.presentation.ui.theme.topRoundedCornerShapeLarge
import com.trianglz.mimar.modules.branches.presentation.model.BranchDetailsUIModel
import com.trianglz.mimar.modules.services.presentation.composables.ServicesPaginatedList
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel


@Composable
fun BranchDetailsScreenContent(
    source: () -> ComposePaginatedListDataSource<ServiceUIModel>,
    branch: () -> BranchDetailsUIModel,
    specialities: () -> List<SpecialtiesUIModel>,
    selectedSpecialityId: () -> Int,
    isRefreshing: @Composable () -> Boolean,
    showNetworkError: @Composable () -> Boolean,
    isSwipeEnabled: () -> Boolean,
    showFilterIcon:  () -> Boolean,
    hasFilterData:  () -> Boolean,
    state: () -> CollapsingToolbarScaffoldState,
    refreshAllScreen: () -> Unit,
    refreshServices: () -> Unit,
    onFilterIconClicked: () -> Unit,
    onAllInfoClicked: () -> Unit,
    onAllReviewsClicked: () -> Unit,
    onNotificationClicked: () -> Unit = {},
    onCartClicked: () -> Unit = {},
    onBackClicked: () -> Unit = {},
    onSpecialityItemClicked: (Int) -> Unit,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
) {

    val imageModifier = remember {
        Modifier
            .graphicsLayer {
                // change alpha as the toolbar size changes
                alpha = state().toolbarState.progress
            }
    }

    val textModifier = remember {
        Modifier
            .graphicsLayer {
                // change alpha as the toolbar size changes
                alpha = 1 - state().toolbarState.progress
            }
    }


    val spaceBetweenItemsMediumDouble: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.spaceBetweenItemsMedium * 2 }
    }


    if (showNetworkError()) {
        Box {
            BaseComposeMainUIComponents.Companion.LocalMainComponent.NetworkError(
                modifier = Modifier
                    .calculateBottomPadding()
                    .verticalScroll(rememberScrollState()),
                addPadding = true,
                onRetry = {
                    refreshAllScreen()
                }
            )

            BranchDetailsToolBar(
                branchName = { branch().name },
                progress = { state().toolbarState.progress },
                modifier = Modifier.align(Alignment.TopCenter)
                    .padding(all = MaterialTheme.dimens.screenGuideDefault),
                textModifier = Modifier.then(textModifier),
                onNotificationClicked = onNotificationClicked,
                onBackClicked = onBackClicked,
                onCartClicked = onCartClicked,
                isLoading = isRefreshing,
                notificationsCount = notificationsCount,
                cartCount = cartCount
            )
        }



    } else {
        Column(
            Modifier
                .fillMaxSize()
                .calculateBottomPadding(50.dp)
                .background(MaterialTheme.colors.surface),
        ) {
            CollapsingToolbarScaffold(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(MaterialTheme.colors.surface),
//                    .scrollable(
//                        orientation = Orientation.Vertical,
//                        // allow to scroll from within the toolbar
//                        state = rememberScrollableState { delta ->
//                            state().toolbarState.dispatchRawDelta(delta)
//                            delta
//                        }
//                    )
                state = state(), // provide the state of the scaffold
                scrollStrategy = ScrollStrategy.ExitUntilCollapsed, // EnterAlways, EnterAlwaysCollapsed, ExitUntilCollapsed are available
                toolbar = {

                    //region AppBar Content
                    BranchDetailsAppBar(
                        containerModifier = { Modifier.then(imageModifier) },
                        showLoading = isRefreshing,
                        branch = branch,
                        onAllInfoClicked = onAllInfoClicked,
                        onAllReviewsClicked = onAllReviewsClicked
                    )
                    //endregion

                    //region Sticky Header
                    BranchDetailsToolBar(
                        branchName = { branch().name },
                        progress = { state().toolbarState.progress },
                        modifier = Modifier.padding(MaterialTheme.dimens.screenGuideDefault).pin(),
                        textModifier = Modifier.then(textModifier),
                        onNotificationClicked = onNotificationClicked,
                        onBackClicked = onBackClicked,
                        onCartClicked = onCartClicked,
                        isLoading = isRefreshing,
                        notificationsCount = notificationsCount,
                        cartCount = cartCount
                    )

                    //endregion
                },
            ) {
                if (specialities().isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.topRoundedCornerShapeLarge)
                            .fillMaxSize()
                            .fillMaxHeight()
                            .background(
                                MaterialTheme.colors.background
                            )
                    ) {

                        Spacer(
                            modifier = Modifier.height(
                                MaterialTheme.dimens.spaceBetweenItemsMedium
                                        + MaterialTheme.dimens.spaceBetweenItemsSmall
                            )
                        )

                        ServicesTitleRow(isRefreshing, showFilterIcon,hasFilterData,onFilterIconClicked)

                        Spacer(modifier = Modifier.height(spaceBetweenItemsMediumDouble()))

                        SelectableList(showLoading = isRefreshing,
                            { specialities() }, { selectedSpecialityId() },onItemClicked =onSpecialityItemClicked)


                        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))
                        if(specialities().none { it.showShimmer }){
                            ServicesPaginatedList(source, isSwipeEnabled, refreshServices)
                        }
                    }

                }


            }
        }
    }
}