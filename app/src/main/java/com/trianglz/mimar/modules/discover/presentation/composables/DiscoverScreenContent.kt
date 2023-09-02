package com.trianglz.mimar.modules.discover.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents.Companion.LocalMainComponent
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarRoundedHeader
import com.trianglz.mimar.common.presentation.selectables.SelectableList
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun DiscoverScreenContent(
    navigator: DestinationsNavigator,
    userModeHandler: UserModeHandler,
    isRefreshing: @Composable () -> Boolean,
    showNetworkError: @Composable () -> Boolean,
    locationAvailable: () -> Boolean,
    source: () -> ComposePaginatedListDataSource<BranchUIModel>,
    categories: () -> List<CategoryUIModel>,
    hasFilterData: () -> Boolean = { false },
    selectedCategoryId: () -> Int,
    refreshBranches: () -> Unit,
    refreshAllScreen: () -> Unit,
    onSearchClicked: () -> Unit,
    onRequestLocationClicked: () -> Unit,
    defaultAddress: (() -> CustomerAddressUIModel?)? = null,
    onChangeAddressClicked: () -> Unit,
    onFilterClicked: (() -> Unit),
    onCategoryClicked: (Int) -> Unit,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
    ) {

    MimarRoundedHeader(
        navigator = navigator,
        userModeHandler = userModeHandler,
        defaultAddress = defaultAddress,
        onSearchClicked = onSearchClicked,
        hasFilterData = hasFilterData,
        screenTitle = R.string.discover,
        changeAddressClicked = onChangeAddressClicked,
        hasFilterIcon = true,
        onFilterClicked = onFilterClicked,
        notificationsCount = notificationsCount,
        cartCount = cartCount
    ) {
        Column {
//            MainHomeContent(isRefreshing(), list = currentList, onSwipedToRefresh, onGetStartedClicked)
            if (showNetworkError()) {
                LocalMainComponent.NetworkError(modifier = Modifier, addPadding = false) {
                    refreshAllScreen()
                }
            } else {
//                ComposeSwipeRefresh(
//                    isRefreshing = isRefreshing(), onRefresh = {
//                        onSwipedToRefresh.invoke()
//                    }
//                ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .calculateBottomPadding(50.dp)
                        .padding(top = 48.dp),

                    ) {

                    SelectableList(showLoading = isRefreshing,
                        { categories() }, { selectedCategoryId() }, onItemClicked = onCategoryClicked)

                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium))

                    if (categories().none { it.showShimmer }) {
                        BranchesPaginatedList(
                            source,
                            { true },
                            onRefresh = refreshBranches,
                            locationAvailable = locationAvailable,
                            onRequestLocationClicked = onRequestLocationClicked,
                            onChangeLocationClicked = onChangeAddressClicked
                        )
                    }
                }

//                }
            }

        }
    }
}
