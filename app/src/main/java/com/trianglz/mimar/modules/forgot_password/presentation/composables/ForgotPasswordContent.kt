package com.trianglz.mimar.modules.forgot_password.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MultiStyleText
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.validation.PhoneNumberValidator
import com.trianglz.mimar.common.presentation.compose_views.MimarLottieAnimation
import com.trianglz.mimar.modules.authentication.presentation.composables.BaseTextField
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState

@Composable
fun ForgotPasswordContent(
    phone: () -> TextFieldState,
    isSubmitButtonEnabled: () -> Boolean,
    onBackButtonClicked: () -> Unit,
    onCountryCodeClicked: () -> Unit,
    onSubmitButtonClicked: () -> Unit

) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        ScreenHeader( isAddPadding = {false},) {
            onBackButtonClicked.invoke()
        }

        LazyColumn(
            Modifier
                .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.screenGuideMedium)
        ) {

            item(key = R.drawable.forgot_password_img) {

                MimarLottieAnimation(
                    lottieFile = R.raw.forgot_password,
                )
//                CoilImage(
//                    imageModel = { R.drawable.forgot_password_img },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .heightIn(280.dp), imageOptions = ImageOptions(
//                        contentScale = ContentScale.FillWidth,
//                        alignment = Alignment.Center,
//                        contentDescription = null,
//                    )
//                )
                Spacer(modifier = Modifier.height(40.dp))
                MultiStyleText(
                    firstText = stringResource(id = R.string.forgot),
                    firstColor = MaterialTheme.colors.secondary,
                    secondText = stringResource(id = R.string.password),
                    secondColor = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.W700
                    ), modifier = Modifier.weight(1F)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge))
                Text(
                    text = stringResource(id = R.string.enter_phone_number_to_reset_password),
                    textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.primary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400
                    )
                )

                Spacer(modifier = Modifier.height(38.dp))

                BaseTextField(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Phone,
                    hint = com.trianglz.core.R.string.phone_number,
                    label = R.string.phone_number_star,
                    textState = phone(),
                    textFieldModifier = Modifier,
                    validator = PhoneNumberValidator(),
                    onLeadingIconClicked = onCountryCodeClicked,
                    onDone = {
                       if(isSubmitButtonEnabled())
                           onSubmitButtonClicked()
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                BaseComposeMainUIComponents.LocalMainComponent.AppButton(
                    modifier = Modifier,
                    text = R.string.submit,
                    enabled = isSubmitButtonEnabled.invoke(),
                    textStyle = MaterialTheme.typography.button,
                    isAddAlphaToDisabledButton = true,
                    disabledColor = MaterialTheme.colors.primary,
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    onSubmitButtonClicked.invoke()
                }

            }
        }
    }
}