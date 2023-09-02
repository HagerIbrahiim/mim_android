package com.trianglz.mimar.modules.notification.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.pagination.ComposePaginatedList
import com.trianglz.mimar.common.presentation.ui.theme.Iron
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.modules.notification.presentation.source.NotificationsListSource
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun NotificationsScreenContent(
    source: () -> NotificationsListSource,
    navigator: DestinationsNavigator?,
    cartCount: State<Int>,
    userModeHandler: UserModeHandler) {

    val listTopPadding: @Composable () -> Dp = {
        MaterialTheme.dimens.spaceBetweenItemsMedium * 2
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .statusBarsPadding()
            .calculateBottomPadding()

    ) {
        HeaderShadow(dividerColor = Iron) { _ ->

            ScreenHeader(
                navigator = navigator,
                userModeHandler = userModeHandler,
                header = { StringWrapper(R.string.notifications) },
                isAddPadding = { false },
                showCartIcon = { true },
                showNotificationIcon = { true },
                modifier = Modifier,
                cartCount = cartCount,
                showDimmedNotificationIcon = { true }
            )
        }


        ComposePaginatedList(
            lazyListModifier = Modifier
                .padding(bottom = MaterialTheme.dimens.screenGuideDefault)
                .fillMaxSize(),
            spacingBetweenItems = MaterialTheme.dimens.spaceBetweenItemsMedium,
            paginatedListSource = source(),
            contentPadding = PaddingValues(
                top = listTopPadding(),
                start = MaterialTheme.dimens.screenGuideDefault,
                end = MaterialTheme.dimens.screenGuideDefault,
                bottom = MaterialTheme.dimens.bottomNavigationHeight,
            ),
            listItem = { _, item ->
               NotificationItem {
                   item
               }

            },
            emptyListPlaceholder = {
                MimarPlaceholder(
                    modifier = { Modifier.fillParentMaxSize() },
                    placeholderImage = R.drawable.notification_placeholder,
                    titleFirstText = { R.string.no_notifications },
                    titleSecondText = { R.string.yet },
                    descriptionText = { StringWrapper(R.string.you_have_not_received_any_notification) },
                )

            },
        )
    }
}
