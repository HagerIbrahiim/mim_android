package com.trianglz.mimar.modules.employees_list.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.swipe_refresh.ComposeSwipeRefresh
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ButtonGradientBackground
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.ui.theme.Iron
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun EmployeesListScreenContent(
    navigator: DestinationsNavigator?,
    navHostController: NavHostController?= null,
    userModeHandler: UserModeHandler,
    list: () -> List<EmployeeUIModel>,
    showSaveBtn: () -> Boolean,
    showNetworkError: () -> Boolean,
    isRefreshing: () -> Boolean,
    onSwipeToRefresh: () -> Unit,
    onSaveClicked: () -> Unit,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
            .calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight)


    ) {
        HeaderShadow(dividerColor = Iron) { _ ->

            ScreenHeader(
                navHostController = navHostController,
                navigator = navigator,
                userModeHandler = userModeHandler,
                header = { StringWrapper(R.string.staff_members) },
                isAddPadding = { false },
                showNotificationIcon = { true },
                showCartIcon = { false },
                modifier = Modifier,
                notificationsCount = notificationsCount,
                cartCount = cartCount,
            )
        }


        if (showNetworkError()) {
            BaseComposeMainUIComponents.LocalMainComponent.NetworkError(
                modifier = Modifier,
                addPadding = false
            ) {
                onSwipeToRefresh()
            }
        } else {
            Box(modifier = Modifier.weight(1f)) {
                ComposeSwipeRefresh(
                    isRefreshing = isRefreshing(), onRefresh = {
                        onSwipeToRefresh.invoke()
                    }
                ) {
                    EmployeeList(list)

                    if (showSaveBtn()) {
                        ButtonGradientBackground(containerModifier = Modifier.align(Alignment.BottomCenter)) {
                            BaseComposeMainUIComponents.LocalMainComponent.AppButton(
                                R.string.save,
                                enabled = true,
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .padding(
                                        horizontal = MaterialTheme.dimens.screenGuideDefault
                                    ),
                                textStyle = MaterialTheme.typography.button,
                                isAddAlphaToDisabledButton = true,
                                disabledColor = MaterialTheme.colors.primary,
                                backgroundColor = MaterialTheme.colors.primary,
                                onClick = onSaveClicked
                            )


                        }
                    }
                }

            }
        }

    }
}

