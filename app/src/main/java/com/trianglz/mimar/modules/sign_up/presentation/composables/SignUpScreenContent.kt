package com.trianglz.mimar.modules.sign_up.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core.presentation.validation.TextInputValidator
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.composables.PasswordMatchErrorItem
import com.trianglz.mimar.common.presentation.compose_views.MultiColorPartiallyClickableText
import com.trianglz.mimar.common.presentation.compose_views.MultiStyleText
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.validation.PhoneNumberValidator
import com.trianglz.mimar.common.presentation.compose_views.MimarLottieAnimation
import com.trianglz.mimar.modules.authentication.presentation.composables.BaseTextField
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState

@Composable
fun SignUpScreenContent(
    phone: () -> TextFieldState,
    password: () -> TextFieldState,
    confirmPassword: () -> TextFieldState,
    nextClickAction: () -> Unit,
    hideIdenticalPasswordMessage: () -> Boolean,
    onTermsAndConditionsClicked: () -> Unit,
    onSkipClicked: () -> Unit,
    onBackPressed: () -> Unit,
    onCountryCodeClicked: () -> Unit,
    onSignInClicked: () -> Unit,

    ) {



    val termsAndConditionTextStyle : @Composable () -> TextStyle = remember {
        {
            MaterialTheme.typography.subtitle1.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        ScreenHeader(modifier =  Modifier.padding(end = MaterialTheme.dimens.screenGuideDefault.minus(8.dp)),
            addSkipBtn = { true },
            onSkipClicked = onSkipClicked,
            isAddPadding = {false}) {
            onBackPressed()
        }

        LazyColumn(
            Modifier
                .fillMaxSize()
                .calculateBottomPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item(key = R.drawable.sign_up_img) {

                val isButtonValid = remember {
                    derivedStateOf {
                        if (password().textFieldValue.value.text.isNotEmpty()) {
                            confirmPassword().isValid.value =
                                password().textFieldValue.value.text == confirmPassword().textFieldValue.value.text

                            confirmPassword().isUserChangedText.value = true
                        }
                        password().isValid.value && confirmPassword().isValid.value && phone().isValid.value
                    }
                }

                Column(
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.screenGuideMedium),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    MimarLottieAnimation(
                        lottieFile = R.raw.sign_up,
                    )
//                    CoilImage(
//                        imageModel = { R.drawable.sign_up_img },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .heightIn(280.dp), imageOptions = ImageOptions(
//                            contentScale = ContentScale.FillWidth,
//                            alignment = Alignment.Center,
//                            contentDescription = null,
//                        )
//                    )
                    Spacer(modifier = Modifier.height(40.dp))

                    MultiStyleText(
                        firstText = stringResource(id = R.string.sign_new),
                        firstColor = MaterialTheme.colors.primary,
                        secondText = stringResource(id = R.string.up),
                        secondColor = MaterialTheme.colors.secondary,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontSize = 26.sp,
                            fontWeight = FontWeight.W700
                        )
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    BaseTextField(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Phone,
                        hint = com.trianglz.core.R.string.phone_number,
                        label = R.string.phone_number_star,
                        textState = phone(),
                        textFieldModifier = Modifier,
                        validator = PhoneNumberValidator(),
                        onLeadingIconClicked = onCountryCodeClicked
                    )

                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    BaseTextField(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password,
                        hint = com.trianglz.core.R.string.password,
                        label = R.string.password_star,
                        textState = password(),
                        validator = TextInputValidator.NewPasswordValidator(),
                        onDone = { },
                    )
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    BaseTextField(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password,
                        hint = R.string.confirm_password,
                        label = R.string.confirm_password_star,
                        textState = confirmPassword(),
                        stopValidUpdate = true,
                        validator = TextInputValidator.NotEmptyTextValidator(),
                        onDone = {
                            if(isButtonValid.value)
                            nextClickAction()
                        },
                    )

                    if (hideIdenticalPasswordMessage().not()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        PasswordMatchErrorItem()
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge))
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.screenGuideXSmall)
                ) {
                    MultiColorPartiallyClickableText(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .padding(
                                vertical = 2.dp,
                                horizontal = MaterialTheme.dimens.screenGuideXSmall
                            ),
                        firstText = stringResource(id = R.string.by_clicking_next),
                        firstColor = MaterialTheme.colors.primary,
                        secondText = stringResource(id = R.string.terms_and_conditions),
                        secondColor = MaterialTheme.colors.secondary,
                        //  textAlign = TextAlign.Start,
                        style = termsAndConditionTextStyle(),
                        secondTextStyle = termsAndConditionTextStyle().copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        maxLines = Int.MAX_VALUE
                    ) {
                        onTermsAndConditionsClicked.invoke()
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium * 2))


                BaseComposeMainUIComponents.LocalMainComponent.AppButton(
                    text = R.string.next, enabled = isButtonValid.value,
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.screenGuideMedium),
                    textStyle = MaterialTheme.typography.button,
                    isAddAlphaToDisabledButton = true,
                    disabledColor = MaterialTheme.colors.primary,
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    nextClickAction.invoke()
                }

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsMedium * 2))

                MultiColorPartiallyClickableText(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clip(MaterialTheme.shapes.small)
                        .padding(MaterialTheme.dimens.innerPaddingXSmall),
                    firstText = stringResource(id = R.string.already_have_account),
                    firstColor = MaterialTheme.colors.onBackground,
                    secondText = stringResource(R.string.sign_in),
                    secondColor = MaterialTheme.colors.secondary,
                    maxLines = 1,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                ) {
                    onSignInClicked.invoke()
                }

                Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge))

            }
        }
    }
}