package com.trianglz.mimar.modules.user_home.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ui.LocalScaffoldPadding
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents.Companion.LocalMainComponent
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.swipe_refresh.ComposeSwipeRefresh
import com.trianglz.mimar.common.presentation.compose_views.MimarRoundedHeader
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenSections
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.appointments.presentation.composables.AppointmentItem
import com.trianglz.mimar.modules.user_home.presentation.model.*
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun UserHomeScreenContent(
    navigator: DestinationsNavigator,
    userModeHandler: UserModeHandler,
    isRefreshing: @Composable () -> Boolean,
    showNetworkError: @Composable () -> Boolean,
    locationEnabled: () -> Boolean,
    currentList: () -> SnapshotStateList<MutableState<out BaseUserHomeUIModel?>>,
    onSwipedToRefresh: () -> Unit,
    onSearchClicked: () -> Unit,
    onGetStartedClicked: () -> Unit,
    onRequestLocationClicked: () -> Unit,
    defaultAddress: (() -> CustomerAddressUIModel?)? = null,
    onChangeAddressClicked: () -> Unit,
    onCategoryItemClicked: (Int) -> Unit,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
    ) {
    val list = remember {
        currentList.invoke()
    }
    MimarRoundedHeader(navigator = navigator, userModeHandler = userModeHandler, defaultAddress = defaultAddress, onSearchClicked = onSearchClicked, changeAddressClicked = onChangeAddressClicked, notificationsCount = notificationsCount, cartCount = cartCount) {
        Column {
            if (showNetworkError()) {
                LocalMainComponent.NetworkError(modifier = Modifier, addPadding = false) {
                    onSwipedToRefresh()
                }
            } else {
                ComposeSwipeRefresh(
                    isRefreshing = isRefreshing(), onRefresh = {
                        onSwipedToRefresh.invoke()
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .calculateBottomPadding(50.dp),
                        contentPadding = PaddingValues(
                            top = 48.dp,
                            bottom = LocalScaffoldPadding.current.calculateBottomPadding()
                        )
                    ) {
                        list.forEachIndexed { index, itemState ->
                            when (val item = itemState.value) {
                                is GuestWelcomeSectionUIModel -> {
                                    if (item.show) {
                                        item(key = item.uniqueId) {
                                            GuestWelcomeComposable {
                                                onGetStartedClicked()
                                            }
                                            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenSections))
                                        }
                                    }
                                }
                                is LastAppointmentSectionUIModel -> {
                                    if (item.appointment != null) {
                                        item(key = item.uniqueId) {
                                            AppointmentItem(appointment = { item.appointment }, modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideDefault))
                                            Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenSections))
                                        }
                                    }
                                }
                                is CategoriesSectionUIModel -> {
                                    if (item.list.isNotEmpty()) {
                                        item(key = item.uniqueId) {

                                            (itemState as? MutableState<CategoriesSectionUIModel>)?.let {
                                                CategoriesSectionItem({ item },onCategoryItemClicked)
                                            }
                                        }
                                    }
                                }
                                is BranchesSectionUIModel -> {
                                    if (item.list.isNotEmpty() || item.branchSectionType == BranchSectionType.Popular ||!locationEnabled()) {
                                        item(key = item.uniqueId) {

                                            (itemState as? MutableState<BranchesSectionUIModel>)?.let {
                                                BranchesSectionItem(locationEnabled, onRequestLocationClicked = onRequestLocationClicked, onChangeLocationClicked = onChangeAddressClicked) { item }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
