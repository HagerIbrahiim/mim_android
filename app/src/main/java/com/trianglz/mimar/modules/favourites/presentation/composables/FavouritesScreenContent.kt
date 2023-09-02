package com.trianglz.mimar.modules.favourites.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents.Companion.LocalMainComponent
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarRoundedHeader
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun FavouritesScreenContent(
    navigator: DestinationsNavigator,
    userModeHandler: UserModeHandler,
    showNetworkError: @Composable () -> Boolean,
    source: () -> ComposePaginatedListDataSource<BranchUIModel>,
    refreshBranches: () -> Unit,
    refreshAllScreen: () -> Unit,
    defaultAddress: (() -> CustomerAddressUIModel?)? = null,
    onDiscoverBranchesClicked: () -> Unit,
    onChangeAddressClicked: () -> Unit={},
    notificationsCount: State<Int>,
    cartCount: State<Int>,
    ) {

    MimarRoundedHeader(
        navigator = navigator,
        userModeHandler = userModeHandler,
        defaultAddress = defaultAddress,
        screenTitle = R.string.favorites,
        addElevation = false,
        changeAddressClicked = onChangeAddressClicked,
        notificationsCount = notificationsCount,
        cartCount = cartCount
    ) {
        Column {
            if (showNetworkError()) {
                LocalMainComponent.NetworkError(modifier = Modifier, addPadding = false) {
                    refreshAllScreen()
                }
            } else {
                FavouritesPaginatedList(
                    modifier = Modifier
                        .fillMaxSize()
                        .calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight),
                    source,
                    onRefresh = refreshBranches,
                    onDiscoverBranchesClicked = onDiscoverBranchesClicked,

                )
            }

        }
    }
}
