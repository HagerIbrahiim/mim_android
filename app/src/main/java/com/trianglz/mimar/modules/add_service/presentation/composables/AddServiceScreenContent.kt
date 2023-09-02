package com.trianglz.mimar.modules.add_service.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarPlaceholder
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.pagination.ComposePaginatedList
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.ui.theme.dividerColor
import com.trianglz.mimar.modules.services.presentation.composables.ServiceItem
import com.trianglz.mimar.modules.services.presentation.source.ServicesListSource
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun AddServiceScreenContent(
    navigator: DestinationsNavigator?,
    userModeHandler: UserModeHandler,
    source: () -> ServicesListSource,
    onCartClicked: () -> Unit = {},
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

        HeaderShadow(dividerColor = MaterialTheme.colors.dividerColor) {
            ScreenHeader(
                navigator = navigator,
                userModeHandler = userModeHandler,
                header = { StringWrapper(R.string.add_another_service) },
                showNotificationIcon = { true },
                showCartIcon = { true },
                isAddPadding = { false },
                onCartClicked = onCartClicked,
                notificationsCount = notificationsCount,
                cartCount = cartCount
            )
        }

        ComposePaginatedList(
            paginatedListSource = source(),
            listItem = { _, item ->
                ServiceItem { item }
            },
            spacingBetweenItems = MaterialTheme.dimens.spaceBetweenItemsMedium,
            contentPadding = PaddingValues(
                top = MaterialTheme.dimens.innerPaddingSmall,
                bottom = MaterialTheme.dimens.screenGuideDefault,
                start = MaterialTheme.dimens.screenGuideDefault,
                end = MaterialTheme.dimens.screenGuideDefault
            ),
            emptyListPlaceholder = {
                Column(
                    modifier = Modifier.fillParentMaxSize()
                ) {
                    MimarPlaceholder(
                        modifier = {
                            Modifier
                                .fillParentMaxWidth()
                                .weight(1f)
                        },
                        animationFile =  R.raw.search_placeholder ,
                        titleFirstText = { R.string.oops },
                        titleSecondText = { R.string.no_services_found },
                    ) {

                    }
                }

            },
        )
    }


}