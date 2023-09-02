package com.trianglz.mimar.common.presentation.compose_views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.core_compose.presentation.helper.LocalNavController
import com.trianglz.core_compose.presentation.images.ImageFromRes
import com.trianglz.core_compose.presentation.shimmer.shimmer
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.Typography
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeMedium
import com.trianglz.mimar.modules.destinations.CartScreenDestination
import com.trianglz.mimar.modules.destinations.NotificationsListScreenDestination
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler


@Composable
fun ScreenHeader(
    navigator: DestinationsNavigator? = null,
    navHostController: NavHostController? = null,
    userModeHandler: UserModeHandler? = null,
    header: () -> StringWrapper? = { null },
    modifier: Modifier = Modifier,
    addSkipBtn: () -> Boolean = { false },
    isAddPadding: () -> Boolean = { true },
    isAddBackButton: () -> Boolean = { true },
    showNotificationIcon: () -> Boolean = { false },
    showCartIcon: () -> Boolean = { false },
    contentColor: Color = MaterialTheme.colors.primary,
    showDimmedNotificationIcon: () -> Boolean = { false },
    onSkipClicked: () -> Unit = {},
    onNotificationClicked: () -> Unit = {
        if (userModeHandler?.isGuestBlocking()?.not() == true) {
            navigator?.navigate(NotificationsListScreenDestination)
        }
    },
    onCartClicked: () -> Unit = {
        if (userModeHandler?.isGuestBlocking()?.not() == true) {
            val previousScreenDestination = navHostController?.previousBackStackEntry?.destination
            if (previousScreenDestination?.route == CartScreenDestination.route) {
                navigator?.popBackStack()
            } else {
                navigator?.navigate(
                    direction = CartScreenDestination(),
                    builder = {
                        launchSingleTop = true
                    }
                )
            }
        }
    },
    showShimmer: () -> Boolean = { false },
    notificationsCount: State<Int> = mutableStateOf(0),
    cartCount: State<Int> = mutableStateOf(0),
    onBackClicked: () -> Unit = { navigator?.popBackStack() }
) {
    val ctx = LocalContext.current

    val notificationsNumber by remember {
        notificationsCount
    }

    val cartServicesNumber by remember {
        cartCount
    }

    val onNotificationBtnClicked: () -> Unit = remember {
        {
            onNotificationClicked()
        }
    }

    val onCartBtnClicked: () -> Unit = remember {
        {
            onCartClicked()
        }
    }
    val onBackBtnClicked = remember {
        {
            onBackClicked()
        }
    }

    val onSkipBtnClicked = remember {
        {
            onSkipClicked()
        }
    }

    val skipBtnVisibility = remember {
        if (addSkipBtn()) Visibility.Visible else Visibility.Gone
    }

    val screenGuideLarge: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.screenGuideLarge }
    }


    val screenGuideSmall: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.screenGuideSmall }
    }

    val screenGuideXSmall: @Composable () -> Dp = remember {
        { MaterialTheme.dimens.screenGuideXSmall }
    }

    val endPadding: @Composable () -> Dp = remember {
        { if (addSkipBtn()) screenGuideSmall() else screenGuideLarge() }
    }

    val headerVisibility = remember {
        if (header() != null)
            Visibility.Visible else Visibility.Gone
    }

    val backBtnVisibility = remember {
        if (isAddBackButton())
            Visibility.Visible else Visibility.Invisible
    }

    val showCartVisibility = remember {
        if (showCartIcon())
            Visibility.Visible else Visibility.Gone
    }

    val showNotificationVisibility = remember {
        if (showNotificationIcon())
            Visibility.Visible else Visibility.Gone
    }

    val notificationImg = remember {
        if (showDimmedNotificationIcon()) R.drawable.ic_notification_filled
        else R.drawable.notification_icon
    }


    val paddingModifier: @Composable () -> Modifier = remember {
        {
            if (isAddPadding()) Modifier.padding(
                start = screenGuideXSmall(),
                end = endPadding()
            ) else
                Modifier
        }
    }

    val titleEndPadding = remember {
        if (showNotificationIcon() && showCartIcon())
            90.dp
        else if (addSkipBtn() || showCartIcon() || showNotificationIcon())
            60.dp
        else
            40.dp
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .then(paddingModifier())
            .then(modifier)
    ) {

        val (backBtn, title, skipBtn, notificationIcon, cartIcon) = createRefs()

        BackButtonCompose(Modifier.constrainAs(backBtn) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            visibility = backBtnVisibility
        }, onclick = onBackBtnClicked, tintColor = contentColor)

        Text(
            modifier = Modifier
                .shimmer(showShimmer())
                .constrainAs(title) {
                    linkTo(parent.top, backBtn.bottom)
                    linkTo(
                        parent.start,
                        parent.end,
                        startMargin = titleEndPadding,
                        endMargin = titleEndPadding,
                    )
                    width = Dimension.fillToConstraints
                    visibility = headerVisibility
                },
            text = header()?.getString(context = ctx) ?: "",
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = Typography.subtitle1.copy(
                color = contentColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            )
        )

        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickWithThrottle(onClick = onSkipBtnClicked)
                .padding(all = 8.dp)
                .constrainAs(skipBtn) {
                    linkTo(title.top, title.bottom)
                    linkTo(title.end, parent.end, bias = 1F)
                    visibility = skipBtnVisibility
                },
            text = stringResource(id = R.string.skip),
            textAlign = TextAlign.End,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = Typography.subtitle1.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W400
            )
        )

        Box(
            modifier = Modifier
                .size(28.dp)
                .constrainAs(notificationIcon) {
                    end.linkTo(cartIcon.start, margin = 14.dp, goneMargin = 14.dp)
                    linkTo(backBtn.top, backBtn.bottom)
                    linkTo(backBtn.top, backBtn.bottom)
                    visibility = showNotificationVisibility
                }) {

            IconButton(
                onClick = onNotificationBtnClicked,
                enabled = showDimmedNotificationIcon().not(),
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = notificationImg),
                    contentDescription = "",
                    modifier = Modifier.size(MaterialTheme.dimens.iconSizeMedium)
                )

            }
            if (notificationsNumber > 0) {
                CircleBadgeItem(
                    modifier = Modifier.align(Alignment.TopEnd),
                    number = notificationsNumber,
                    hideNumber = true,
                )
            }
        }
        Box(modifier = Modifier
            .size(28.dp)
            .constrainAs(cartIcon) {
                end.linkTo(parent.end, 14.dp)
                linkTo(backBtn.top, backBtn.bottom)
                visibility = showCartVisibility

            }) {

            IconButton(
                onClick = onCartBtnClicked,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.cart_icon),
                    contentDescription = "",
                    modifier = Modifier.size(MaterialTheme.dimens.iconSizeMedium)
                )

            }
            if (cartServicesNumber > 0) {
                CircleBadgeItem(
                    modifier = Modifier.align(Alignment.TopEnd),
                    number = cartServicesNumber
                )
            }
        }

    }
}
