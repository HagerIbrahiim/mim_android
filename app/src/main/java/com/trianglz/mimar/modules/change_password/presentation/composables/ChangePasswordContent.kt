package com.trianglz.mimar.modules.change_password.presentation.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core.presentation.validation.TextInputValidator
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.composables.PasswordMatchErrorItem
import com.trianglz.mimar.common.presentation.compose_views.MultiStyleText
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.extensions.ifTrue
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.common.presentation.compose_views.MimarLottieAnimation
import com.trianglz.mimar.modules.authentication.presentation.composables.BaseTextField
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.common.presentation.validation.NewPasswordValidator
import com.trianglz.mimar.common.presentation.validation.OldPasswordValidator

@Composable
fun ChangePasswordContent(
    fromHome: Boolean,
    currentPassword: TextFieldState,
    newPassword: TextFieldState,
    confirmPassword: TextFieldState,
    btnText: Int,
    isSendEnabled: () -> Boolean,
    hideIdenticalPasswordMessage: () -> Boolean,
    onBackClicked: () -> Unit,
    onSendClicked: () -> Unit,
) {


    BackHandler {
        onBackClicked.invoke()
    }

    val bottomSpacerHeight : @Composable () -> Dp = remember(fromHome) {
        {
            MaterialTheme.dimens.screenGuideLarge.plus(
                if(fromHome) MaterialTheme.dimens.bottomNavigationHeight
                else 0.dp
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .statusBarsPadding().ifTrue(fromHome){
                Modifier.imePadding()
            }
    ) {

        ScreenHeader(isAddPadding = {false}) {
            onBackClicked.invoke()
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(PaddingValues(horizontal = MaterialTheme.dimens.screenGuideMedium)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //item(key = R.drawable.forgot_password_img) {

            MimarLottieAnimation(
                lottieFile = R.raw.reset_password,
            )

//            CoilImage(
//                imageModel = { R.drawable.change_password_image },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .heightIn(280.dp), imageOptions = ImageOptions(
//                    contentScale = ContentScale.FillWidth,
//                    alignment = Alignment.Center,
//                    contentDescription = null,
//                )
//            )

            Spacer(modifier = Modifier.height(40.dp))
            MultiStyleText(
                firstText = stringResource(id = R.string.change),
                firstColor = MaterialTheme.colors.primary,
                secondText = stringResource(id = R.string.password),
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
                keyboardType = KeyboardType.Password,
                label = R.string.current_password_star,
                hint = R.string.current_password,
                textState = currentPassword,
                validator = OldPasswordValidator(),

            )


            Spacer(modifier = Modifier.height(20.dp))

            BaseTextField(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password,
                hint = R.string.new_password,
                label = R.string.new_password_star,
                textState = newPassword,
                validator = NewPasswordValidator(),
                onDone = { },
            )

            Spacer(modifier = Modifier.height(20.dp))

            BaseTextField(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password,
                hint = R.string.confirm_new_password,
                label = R.string.confirm_password_star,
                textState = confirmPassword,
                stopValidUpdate = true,
                validator = TextInputValidator.NotEmptyTextValidator(),
                onDone = {
                    if (isSendEnabled())
                        onSendClicked.invoke()
                },
            )

            if (hideIdenticalPasswordMessage().not()) {
                Spacer(modifier = Modifier.height(16.dp))
                PasswordMatchErrorItem()
            }
            Spacer(modifier = Modifier.height(20.dp))


            BaseComposeMainUIComponents.LocalMainComponent.AppButton(
                modifier = Modifier,
                text = btnText,
                enabled = isSendEnabled(),
                textStyle = MaterialTheme.typography.button,
                isAddAlphaToDisabledButton = true,
                disabledColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                onSendClicked.invoke()
            }
            Spacer(modifier = Modifier.height(bottomSpacerHeight()))

        }
        //}
    }
}