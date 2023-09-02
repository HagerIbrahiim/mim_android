package com.trianglz.mimar.modules.branch_reviews.presentation

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.branch_reviews.presentation.composables.BranchReviewsScreenContent
import com.trianglz.mimar.modules.branch_reviews.presentation.contract.BranchReviewsEvent
import com.trianglz.mimar.modules.branch_reviews.presentation.contract.BranchReviewsState
import com.trianglz.mimar.modules.branch_reviews.presentation.contract.BranchReviewsViewState
import com.trianglz.mimar.modules.branch_reviews.presentation.model.BranchReviewsNavArgs

@MainGraph
@Destination(navArgsDelegate = BranchReviewsNavArgs::class)
@Composable
fun BranchReviewsScreen(
    viewModel: BranchReviewsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    //region Status Bar
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )

        onDispose {}
    }
    //endregion
    val viewStates = remember(viewModel.viewStates) {
        viewModel.viewStates ?: BranchReviewsViewState()
    }
    val branchName by remember {
        viewStates.branchName
    }
    val rating by remember {
        viewStates.rating
    }
    val numOfReviews by remember {
        viewStates.numOfReviews
    }
    val isRefreshing = viewStates.isRefreshing.collectAsState()
//    val showNetworkError = viewStates.networkError.collectAsState()

    val onSwipedToRefreshTriggered = remember {
        {
            viewModel.setEvent(BranchReviewsEvent.RefreshScreen)
        }
    }

    BranchReviewsScreenContent(
        navigator = navigator,
        userModeHandler = viewModel.userModeHandler,
        source = viewModel.source,
        branchName = { branchName.orEmpty() },
        isRefreshing = { isRefreshing.value },
        onSwipeToRefresh = onSwipedToRefreshTriggered,
        rating = { rating.orEmpty() },
        numOfReviews = { numOfReviews.orEmpty() },
        notificationsCount = viewModel.notificationCount,
        cartCount = viewModel.cartCount
    )

    GeneralObservers<BranchReviewsState, BranchReviewsViewModel>(viewModel = viewModel) {

    }
}


