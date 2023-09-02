package com.trianglz.mimar.modules.sign_in.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.ImageItem
import com.trianglz.mimar.modules.authentication.presentation.composables.SkipButtonWithLanguageBtn
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState

@Composable
fun SignInScreenContent(
    phone: () -> TextFieldState,
    password: () -> TextFieldState,
    currentLocale: @Composable ()() -> String,
    isSignInButtonEnabled: () -> Boolean,
    onForgotPasswordClicked: () -> Unit,
    onSignInClicked: () -> Unit,
    onFacebookClicked: () -> Unit,
    onGoogleClicked: () -> Unit,
    onRegisterNowClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    onCountryCodeClicked: () -> Unit,
    changeLanguageClicked: () -> Unit,

    ) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .calculateBottomPadding()
            .statusBarsPadding()
    ) {

        item {
            Spacer(modifier = Modifier.padding(top = 40.dp))


            SkipButtonWithLanguageBtn(currentLocale, onSkipClicked,
                modifier = Modifier.padding(start = MaterialTheme.dimens.screenGuideDefault,
                end = MaterialTheme.dimens.screenGuideDefault / 2),
                changeLanguageClicked =  changeLanguageClicked)


            Spacer(modifier = Modifier.size(28.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.screenGuideMedium)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ImageItem(
                        image = R.drawable.ic_mimar_logo,
                        modifier = Modifier
                            .height(28.dp)
                            .width(82.dp), animation = CrossfadePlugin()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(id = R.string.welcome_get_started),
                        textAlign = TextAlign.End, overflow = TextOverflow.Ellipsis, maxLines = 1,
                        style = MaterialTheme.typography.subtitle1.copy(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W400
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            SignInDataComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillParentMaxHeight(),
                phone = phone,
                password = password,
                isSignInButtonEnabled = isSignInButtonEnabled,
                onForgotPasswordClicked = onForgotPasswordClicked,
                onSignInClicked = onSignInClicked,
                onFacebookClicked = onFacebookClicked,
                onGoogleClicked = onGoogleClicked,
                onRegisterNowClicked = onRegisterNowClicked,
                onCountryCodeClicked = onCountryCodeClicked,
            )
        }
    }
}