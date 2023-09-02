package com.trianglz.mimar.modules.addresses_list.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ButtonGradientBackground
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.extensions.ifTrue
import com.trianglz.mimar.common.presentation.ui.theme.Iron
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.modules.addresses.ui.composables.AddressPaginatedList
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.addresses_list.presentation.source.AddressesListSource
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun AddressesListScreenContent(
    navigator: DestinationsNavigator?,
    userModeHandler: UserModeHandler,
    showAddAddress: () -> Boolean,
    source: () -> AddressesListSource,
    fromHome: () -> Boolean,
    onSkipClicked: () -> Unit,
    addNewAddressButtonClicked: () -> Unit,
    onChangeDefaultAddressClicked: (CustomerAddressUIModel) -> Unit = {},
    onEditAddressClicked: (CustomerAddressUIModel) -> Unit = {},
    onDeleteAddressClicked: (Int) -> Unit = {},
    notificationsCount: State<Int>,
    cartCount: State<Int>,
) {


    val buttonHeight: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.buttonHeight }
    }

    val screenGuideSpacing: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.screenGuideDefault }
    }

    val listBottomPadding: @Composable () -> Dp = remember(source().loadingState.value) {
        {
            if (showAddAddress())
                buttonHeight() + screenGuideSpacing() * 2  else 0.dp
        }
    }


    val headerPaddingModifier: @Composable () -> Modifier = remember {
        { if (fromHome()) Modifier else Modifier.padding(end = screenGuideSpacing() - 8.dp) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .statusBarsPadding()
            .calculateBottomPadding()
            .ifTrue(fromHome()){
                Modifier.padding(bottom = MaterialTheme.dimens.bottomNavigationHeight)
            }


    ) {
        HeaderShadow(dividerColor = Iron) { _ ->

            ScreenHeader(
                navigator = navigator,
                userModeHandler = userModeHandler,
                header = { StringWrapper(R.string.saved_addresses) },
                isAddPadding = { false },
                addSkipBtn = { !fromHome() },
                showCartIcon = { fromHome() },
                showNotificationIcon = { fromHome() },
                modifier = headerPaddingModifier(),
                onSkipClicked = onSkipClicked,
                notificationsCount = notificationsCount,
                cartCount = cartCount
            )
        }



        Box(modifier = Modifier.weight(1f)) {

            AddressPaginatedList(
                source,
                listBottomPadding,
                addNewAddressButtonClicked =addNewAddressButtonClicked,
                onChangeDefaultAddressClicked = onChangeDefaultAddressClicked,
                onEditAddressClicked = onEditAddressClicked,
                onDeleteAddressClicked = onDeleteAddressClicked,
            )

            if (showAddAddress()) {
                ButtonGradientBackground(containerModifier = Modifier.align(Alignment.BottomCenter)) {
                    BaseComposeMainUIComponents.LocalMainComponent.AppButton(
                        R.string.add_new_address,
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
                        onClick = addNewAddressButtonClicked
                    )
                }

            }


        }

    }

}
