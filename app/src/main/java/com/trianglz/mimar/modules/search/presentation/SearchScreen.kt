package com.trianglz.mimar.modules.search.presentation

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.destinations.BranchDetailsScreenDestination
import com.trianglz.mimar.modules.search.presentation.composables.SearchScreenContent
import com.trianglz.mimar.modules.search.presentation.contract.SearchEvent
import com.trianglz.mimar.modules.search.presentation.contract.SearchState
import com.trianglz.mimar.modules.search.presentation.contract.SearchViewState
import com.trianglz.mimar.modules.search.presentation.model.SearchNavArgs

@MainGraph
@Composable
@Preview
@Destination(navArgsDelegate = SearchNavArgs::class)
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null
) {

    val viewStates = remember {
        viewModel.viewStates ?: SearchViewState()
    }
    val searchText = remember {
        viewStates.searchText
    }

    val onBackBtnClicked = remember {
        {
            viewModel.setEvent(SearchEvent.BackIconClicked)
        }
    }

    val showProvidersTab = remember {
        viewStates.showProvidersTab
    }

    val tabList = remember {
        viewStates.tabList
    }

    val onTabChanged: (Int) -> Unit = remember {
        {
            viewModel.setEvent(SearchEvent.TabChanged(it))
        }
    }

    val paginationSource = remember(showProvidersTab.value) {
        if(showProvidersTab.value)
            viewModel.branchesSource else viewModel.servicesSource
    }


    val listState = rememberLazyListState()

    LaunchedEffect(key1 = viewStates.scrollToFirstPosition.value) {
        if (viewStates.scrollToFirstPosition.value) {
            listState.scrollToItem(0)
            viewStates.scrollToFirstPosition.value = false
        }

    }

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


    SearchScreenContent(
        { listState },
        { tabList },
        { showProvidersTab.value },
        { paginationSource },
        { searchText },
        onTabChanged,
        onBackBtnClicked

    )



    GeneralObservers<SearchState, SearchViewModel>(viewModel = viewModel) {
        when (it) {
            is SearchState.OpenBranchDetails -> {
                navigator?.navigate(BranchDetailsScreenDestination(it.branchId,it.specialityId))
            }
            SearchState.FinishScreen -> {
                navigator?.popBackStack()
            }
        }

    }

    HandleLoadingStateObserver(viewModel = viewModel) {

    }

}

