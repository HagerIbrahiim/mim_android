package com.trianglz.mimar.modules.sign_in.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core.presentation.validation.TextInputValidator
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents.Companion.LocalMainComponent
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.CardWithRoundedTopCorners
import com.trianglz.mimar.common.presentation.compose_views.ImageItem
import com.trianglz.mimar.common.presentation.compose_views.MultiColorPartiallyClickableText
import com.trianglz.mimar.common.presentation.compose_views.MultiStyleText
import com.trianglz.mimar.common.presentation.validation.PhoneNumberValidator
import com.trianglz.mimar.modules.authentication.presentation.composables.BaseTextField
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState


@Composable
fun SignInDataComposable(
    modifier: Modifier = Modifier,
    phone: () -> TextFieldState,
    password: () -> TextFieldState,
    isSignInButtonEnabled: () -> Boolean,
    onForgotPasswordClicked: () -> Unit,
    onSignInClicked: () -> Unit,
    onFacebookClicked: () -> Unit,
    onGoogleClicked: () -> Unit,
    onRegisterNowClicked: () -> Unit,
    onCountryCodeClicked: () -> Unit,
) {

    CardWithRoundedTopCorners(
        modifier = modifier.fillMaxSize(),
        clickable = null,
        backgroundColor = Color.White,
        borderColor = Color.White
    ) {
        Column(
            Modifier
                .fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(44.dp))
            MultiStyleText(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(horizontal = MaterialTheme.dimens.screenGuideMedium),
                firstText = stringResource(id = R.string.sign),
                firstColor = MaterialTheme.colors.primary,
                secondText = stringResource(id = R.string.`in`),
                secondColor = MaterialTheme.colors.secondary
            )

            Spacer(modifier = Modifier.height(22.dp))
            Column(modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideMedium)) {


                BaseTextField(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Phone,
                    hint = com.trianglz.core.R.string.phone_number,
                    label = R.string.phone_number_star,
                    textState = phone(),
                    textFieldModifier = Modifier,
                    validator = PhoneNumberValidator(),
                    onLeadingIconClicked = onCountryCodeClicked,
                )

                Spacer(modifier = Modifier.padding(top = MaterialTheme.dimens.screenGuideLarge))

                BaseTextField(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                    hint = com.trianglz.core.R.string.password,
                    label = R.string.password_star,
                    textState = password(),
                    validator = TextInputValidator.NotEmptyTextValidator(),
                    onDone = {
                        if (isSignInButtonEnabled())
                            onSignInClicked()
                    },
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.innerPaddingXSmall)
            ) {
                Text(
                    modifier = Modifier
                        .align(TopEnd)
                        .clip(MaterialTheme.shapes.small)
                        .clickWithThrottle {
                            onForgotPasswordClicked.invoke()
                        }
                        .padding(
                            vertical = 4.dp,
                            horizontal = MaterialTheme.dimens.innerPaddingXSmall
                        ),
                    text = stringResource(id = R.string.forgot_password),
                    textAlign = TextAlign.End, overflow = TextOverflow.Ellipsis, maxLines = 1,
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.secondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                )
            }
            Spacer(modifier = Modifier.height(26.dp))

            LocalMainComponent.AppButton(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideMedium),
                text = R.string.sign_in,
                enabled = isSignInButtonEnabled(),
                textStyle = MaterialTheme.typography.button,
                isAddAlphaToDisabledButton = true,
                disabledColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                onSignInClicked.invoke()
            }
            Spacer(modifier = Modifier.height(36.dp))
            SignUpWithDividersSection(Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideMedium))
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.screenGuideMedium),
                horizontalArrangement = Arrangement.Center
            ) {
                ImageItem(
                    image = R.drawable.ic_facebook,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickWithThrottle {
                            onFacebookClicked.invoke()
                        }
                        .padding(4.dp), applyShimmer = false
                )

                Spacer(modifier = Modifier.size(48.dp))

                ImageItem(
                    image = R.drawable.ic_google,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .clickWithThrottle {
                            onGoogleClicked.invoke()
                        }
                        .padding(4.dp), applyShimmer = false
                )


            }

            MultiColorPartiallyClickableText(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .clip(MaterialTheme.shapes.small)
                    .padding(MaterialTheme.dimens.innerPaddingXSmall),
                firstText = stringResource(id = R.string.i_dont_have_account),
                firstColor = MaterialTheme.colors.onBackground,
                secondText = stringResource(R.string.register_now),
                secondColor = MaterialTheme.colors.secondary,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400
                )
            ) {
                onRegisterNowClicked.invoke()
            }
        }
    }
}
