package com.trianglz.mimar.modules.verification.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MultiStyleText
import com.trianglz.mimar.common.presentation.compose_views.ScreenHeader
import com.trianglz.mimar.common.presentation.compose_views.MimarLottieAnimation

@Composable
fun VerificationScreenContent(
    onTimerClicked: () -> Unit,
    onSubmitClicked: () -> Unit,
    enableTimer: () -> State<Boolean>,
    otp: () -> MutableState<String>,
    onBackPressed: () -> Unit,
    phone: () -> MutableState<String>, timeInSeconds: () -> MutableState<Int>,
    timeInMinutes: () -> MutableState<Long>,
    onTextChanged: (String) -> Unit,
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        ScreenHeader( isAddPadding = {false},) {
            onBackPressed()
        }

        LazyColumn(
            Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.screenGuideMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item(key = R.drawable.forgot_password_img) {

                MimarLottieAnimation(
                    lottieFile = R.raw.verification,
                )
//                CoilImage(
//                    imageModel = { R.drawable.verification_img },
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
                    firstText = stringResource(id = R.string.verification),
                    firstColor = MaterialTheme.colors.primary,
                    secondText = stringResource(id = R.string.code),
                    secondColor = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.W700
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.weight(1F),
                    text = stringResource(id = R.string.please_enter_verification, phone().value),
                    textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.primary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400
                    )
                )

                Spacer(modifier = Modifier.height(28.dp))


                OtpTextField(
                    modifier = Modifier.fillMaxWidth(),
                    otpText = otp(),
                    onOTPFilled =
                    onSubmitClicked
                ) { value, _ ->
//                    viewStates.otp.value = value
                    onTextChanged.invoke(value)
                }

                Spacer(modifier = Modifier.height(20.dp))

                BaseComposeMainUIComponents.LocalMainComponent.AppButton(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.screenGuideMedium),
                    text = R.string.next,
                    enabled = otp().value.length == 5,
                    textStyle = MaterialTheme.typography.button,
                    isAddAlphaToDisabledButton = true,
                    disabledColor = MaterialTheme.colors.primary,
                    backgroundColor = MaterialTheme.colors.primary,
                ) {
                    onSubmitClicked.invoke()
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.screenGuideLarge))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.have_not_received_verification),
                    textAlign = TextAlign.Center, overflow = TextOverflow.Ellipsis, maxLines = 1,
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))



                TimerItem(
                    enabled = enableTimer,
                    timeInMinutes = timeInMinutes,
                    timeInSeconds = timeInSeconds,
                    onClick = {
                        onTimerClicked.invoke()
                    },
                )

            }
        }
    }
}