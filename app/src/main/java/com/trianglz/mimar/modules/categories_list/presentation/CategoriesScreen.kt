package com.trianglz.mimar.modules.categories_list.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.NavGraphs
import com.trianglz.mimar.modules.categories_list.presentation.composables.CategoriesScreenContent
import com.trianglz.mimar.modules.categories_list.presentation.contract.CategoriesEvent
import com.trianglz.mimar.modules.categories_list.presentation.contract.CategoriesState
import com.trianglz.mimar.modules.categories_list.presentation.contract.CategoriesViewState
import com.trianglz.mimar.modules.destinations.DiscoverScreenDestination


@MainGraph(start = false) // sets this as the start destination of the default nav graph
@Destination
@Composable
fun CategoriesScreen(
    navigator: DestinationsNavigator,
    viewModel: CategoriesViewModel = hiltViewModel(),
) {

    val viewStates = remember {
        viewModel.viewStates ?: CategoriesViewState()
    }


    val paginatedSource = remember {
        viewStates.scource.value
    }

    val onCategoryItemClicked: (Int) -> Unit = remember {
        {
            viewModel.setEvent(CategoriesEvent.OnCategoryClicked(it))
        }
    }

    CategoriesScreenContent(
        navigator = navigator,
        userModeHandler = viewModel.userModeHandler,
        paginatedSource = { paginatedSource },
        onCategoryItemClicked = onCategoryItemClicked,
        notificationsCount = viewModel.notificationCount,
        cartCount = viewModel.cartCount
    )

    GeneralObservers<CategoriesState, CategoriesViewModel>(viewModel = viewModel) {
        when (it) {
            CategoriesState.Idle -> {}

            is CategoriesState.OpenCategory -> {

                navigator.navigate(DiscoverScreenDestination(categoryId = it.id)){

                    // Pop up to the root of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(NavGraphs.mainGraph) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

                navigator.navigate(DiscoverScreenDestination(categoryId =it.id)){
                    popUpTo(NavGraphs.mainGraph)
                }

            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {}

}