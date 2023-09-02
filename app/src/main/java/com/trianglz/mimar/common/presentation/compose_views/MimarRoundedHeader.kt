package com.trianglz.mimar.common.presentation.compose_views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.presentation.helper.MultipleEventsCutter
import com.trianglz.core.presentation.helper.get
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.core_compose.presentation.extensions.setStatusBarPadding
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.headerElevation
import com.trianglz.mimar.common.presentation.ui.theme.logoImageSize
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXXLarge
import com.trianglz.mimar.common.presentation.ui.theme.xxSmall
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.destinations.CartScreenDestination
import com.trianglz.mimar.modules.destinations.NotificationsListScreenDestination
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler


@Composable
fun MimarRoundedHeader(
    navigator: DestinationsNavigator,
    userModeHandler: UserModeHandler,
    defaultAddress: (() -> CustomerAddressUIModel?)? = null,
    onSearchClicked: (() -> Unit)? = null,
    changeAddressClicked: () -> Unit = {},
    hasFilterIcon: Boolean = false,
    hasFilterData: () -> Boolean = { false },
    screenTitle: Int? = null,
    onFilterClicked: (() -> Unit)? = null,
    addElevation: Boolean = true,
    welcomeText: Int? = R.string.welcome,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
    content: @Composable () -> Unit
) {

    val headerView: @Composable () -> Unit = remember(defaultAddress?.invoke()) {
        {
            if (defaultAddress?.invoke() == null) {
                welcomeText?.let {
                    Text(
                        text = stringResource(id = welcomeText),
                        style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSecondary),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                    )
                }

            } else {
                AddressSelectionItem(defaultAddress, changeAddressClicked)
            }
        }

    }

    val headerViewBottomPadding: @Composable () -> Dp = remember(defaultAddress?.invoke()) {
        {
            if (defaultAddress?.invoke() == null && welcomeText != null || defaultAddress?.invoke() != null) {
                MaterialTheme.dimens.spaceBetweenItemsXLarge
            } else {
                MaterialTheme.dimens.spaceBetweenItemsXLarge.minus(6.dp)
            }
        }
    }

    val contentTopPadding: @Composable () -> Dp = remember {
        {
            if (onSearchClicked == null) 0.dp else MaterialTheme.dimens.spaceBetweenItemsXLarge
        }
    }

    val headerElevation: @Composable () -> Dp = remember {
        {
            if (addElevation) MaterialTheme.dimens.headerElevation else 0.dp
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .setStatusBarPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXXXLarge))
        HeaderHomeComposable(
            navigator,
            userModeHandler,
            screenTitle,
            notificationsCount = notificationsCount,
            cartCount = cartCount
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXLarge))
        headerView()
        Spacer(modifier = Modifier.height(headerViewBottomPadding()))
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .padding(top = contentTopPadding())
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background, shape = MaterialTheme.shapes.large)
                    .clip(MaterialTheme.shapes.large)
                    .shadow(elevation = headerElevation())
            ) {
                content()
            }
            onSearchClicked?.let {
                SearchTextField(
                    searchText = { mutableStateOf("") },
                    enabled = { false },
                    background = { MaterialTheme.colors.surface },
                    elevation = { MaterialTheme.dimens.cardElevation },
                    hintText = { stringResource(id = R.string.looking_for_something) },
                    hasFilterIcon = hasFilterIcon,
                    hasFilterData = hasFilterData,
                    onFilterClicked = onFilterClicked
                ) {
                    onSearchClicked.invoke()
                }
            }


        }
    }
}

@Composable
private fun HeaderHomeComposable(
    navigator: DestinationsNavigator,
    userModeHandler: UserModeHandler,
    screenTitle: Int? = null,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
    onNotificationsClicked: () -> Unit = {
        if (userModeHandler.isGuestBlocking().not()) {
            navigator.navigate(NotificationsListScreenDestination)
        }
    },
    onCartClicked: () -> Unit = {
        if (userModeHandler.isGuestBlocking().not()) {
            navigator?.navigate(
                direction = CartScreenDestination(),
                builder = {
                    launchSingleTop = true
                }
            )
        }
    },
) {

    val multipleEventsCutter = remember { MultipleEventsCutter.get() }

    val notificationsNumber by remember {
        notificationsCount
    }

    val cartServicesNumber by remember {
        cartCount
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.dimens.screenGuideDefault)
    ) {
        screenTitle?.let {
            Text(
                text = stringResource(id = screenTitle),
                modifier = Modifier
                    .align(Alignment.Center),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.W700,
                    color = MaterialTheme.colors.onSecondary
                ), maxLines = 1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        } ?: run {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_mimar_logo),
                contentDescription = "logo_image",
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(MaterialTheme.dimens.logoImageSize)
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterEnd), Arrangement.spacedBy(16.dp)) {
            Box(modifier = Modifier.size(28.dp)) {

                IconButton(modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center), onClick = {
                    multipleEventsCutter.clickWithThrottle {
                        onNotificationsClicked()
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_notifications),
                        contentDescription = "",
                        tint = MaterialTheme.colors.onPrimary
                    )

                }
                if (notificationsNumber > 0) {
                    CircleBadgeItem(
                        modifier = Modifier.align(Alignment.TopEnd),
                        borderColor = MaterialTheme.colors.primary,
                        number = notificationsNumber,
                        hideNumber = true,
                    )
                }
            }
            Box(modifier = Modifier.size(28.dp)) {

                IconButton(modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center), onClick = {
                    multipleEventsCutter.clickWithThrottle {
                        onCartClicked()
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_cart),
                        contentDescription = "",
                        tint = MaterialTheme.colors.onPrimary
                    )

                }
                if (cartServicesNumber > 0) {
                    CircleBadgeItem(
                        modifier = Modifier.align(Alignment.TopEnd),
                        borderColor = MaterialTheme.colors.primary,
                        number = cartServicesNumber
                    )
                }
            }

        }

    }
}

@Composable
private fun AddressSelectionItem(
    defaultAddress: (() -> CustomerAddressUIModel?)?,
    action: () -> Unit
) {
    val context = LocalContext.current

    val item = remember(defaultAddress?.invoke()) {
        defaultAddress?.invoke()
    }

    val address = remember(defaultAddress?.invoke()) {
        if (!item?.country.isNullOrEmpty())
            "${item?.buildingNumber} ${item?.streetName} ${item?.district} ${item?.city}, ${item?.country}"
        else "${item?.title?.getString(context)}"
    }

    Row(modifier = Modifier
        .padding(horizontal = MaterialTheme.dimens.screenGuideSmall)
        .background(Color.Transparent, shape = MaterialTheme.shapes.xxSmall)
        .clip(shape = MaterialTheme.shapes.xxSmall)
        .clickWithThrottle { action() }
        .padding(all = MaterialTheme.dimens.spaceBetweenItemsXSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextStartsWithIcon(
            drawableRes = R.drawable.ic_location,
            iconTint = MaterialTheme.colors.secondary,
            data = address,
            textColor = Color.White,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(10.dp))

//        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_down),
            contentDescription = null,
            tint = MaterialTheme.colors.onSecondary,
            modifier = Modifier
                .requiredSizeIn(12.dp)
                .clip(CircleShape),
        )
    }
}
