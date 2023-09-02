package com.trianglz.mimar.modules.add_address.presentation.composables

import android.location.Location
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.composables.HeaderShadow
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.ui.theme.Iron
import com.trianglz.mimar.common.presentation.ui.theme.Typography
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.ui.theme.spaceBetweenItemsXXLarge
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.map.presentation.model.MapScreenMode
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun AddAddressScreenContent(
    navigator: DestinationsNavigator?,
    userModeHandler: UserModeHandler,
    mode: ()-> MapScreenMode,
    currentLocation: () -> Location?,
    addressTitle: () -> TextFieldState,
    country: () -> TextFieldState,
    city: () -> TextFieldState,
    streetName: () -> TextFieldState,
    buildingNum: () -> TextFieldState,
    district: () -> TextFieldState,
    secondaryNum: () -> TextFieldState,
    landmarkNotes: () -> TextFieldState,
    isButtonValid: () -> Boolean,
    fromHome: () -> Boolean,
    editAddressClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    onBackClicked: () -> Unit,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
) {

    val headerText = remember {
        if(mode() == MapScreenMode.AddAddress)  R.string.add_address else R.string.edit_address
    }
    val headerEndPadding: @Composable () -> Dp =  remember {
        { MaterialTheme.dimens.screenGuideXSmall / 2 }
    }

    val headerModifier : @Composable () -> Modifier = remember {
        {
            if (!fromHome()) Modifier.padding(end = headerEndPadding())
            else Modifier
        }
    }

    val bottomPadding : @Composable () -> Dp = remember {
        {
            if (fromHome()) MaterialTheme.dimens.bottomNavigationHeight else 0.dp
        }
    }


    BackHandler {
        onBackClicked()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(MaterialTheme.colors.surface)
            .calculateBottomPadding(bottomPadding())



    ) {

        HeaderShadow(dividerColor = Iron){ _ ->
            ScreenHeader(
                navigator = navigator,
                userModeHandler = userModeHandler,
                header ={ StringWrapper(headerText) },
                modifier = headerModifier(),
                isAddPadding = { false },
                showCartIcon = { fromHome() },
                showNotificationIcon = { fromHome() },
                onBackClicked = onBackClicked,
                notificationsCount = notificationsCount,
                cartCount = cartCount
            )
        }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

            Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.spaceBetweenItemsXXLarge))

            Text(
                text = stringResource(id = R.string.map_location),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.screenGuideDefault),
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Typography.subtitle1.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W600
                )
            )

            Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.spaceBetweenItemsXXLarge))

            EditMapContainer(currentLocation, editAddressClicked)

            AddAddressTextFields(
                addressTitle,
                country,
                city,
                streetName,
                buildingNum,
                district,
                secondaryNum,
                landmarkNotes,
                isButtonValid,
                onSaveClicked
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideDefault))

        }


    }
}