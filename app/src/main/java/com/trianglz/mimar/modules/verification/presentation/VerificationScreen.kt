package com.trianglz.mimar.modules.verification.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.presentation.extensions.showLogoutDialog
import com.trianglz.mimar.common.presentation.navigation.AuthGraph
import com.trianglz.mimar.modules.destinations.SetupProfileAuthScreenDestination
import com.trianglz.mimar.modules.destinations.SignInScreenDestination
import com.trianglz.mimar.modules.verification.presentation.composables.VerificationScreenContent
import com.trianglz.mimar.modules.verification.presentation.contract.VerificationEvent
import com.trianglz.mimar.modules.verification.presentation.contract.VerificationState
import com.trianglz.mimar.modules.verification.presentation.contract.VerificationViewState


@OptIn(ExperimentalComposeUiApi::class)
@Composable
@AuthGraph
@Destination
fun VerificationScreen(
    viewModel: VerificationViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    //region Members and Action
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val viewStates = viewModel.viewStates ?: VerificationViewState()

    BackHandler {
        viewModel.setEvent(VerificationEvent.BackPresses)
    }

    val onTimerClicked = remember {
        {
            viewModel.setEvent(VerificationEvent.TimerClicked)
        }
    }

    val onSubmitClicked = remember {
        {
            keyboardController?.hide()
            viewModel.setEvent(VerificationEvent.SubmitClicked)
        }
    }

    val otp = remember {
        {
            viewStates.otp
        }
    }


    val enableTimer = remember {
        viewStates.isResendEnabled

    }

    val onBackPressed = remember {
        {
            viewModel.setEvent(VerificationEvent.BackPresses)
        }
    }

    val timeInMinutes = remember {
        {
            viewStates.timeInMinutes
        }
    }

    val timeInSeconds = remember {
        {
            viewStates.timeInSeconds
        }
    }

    val phone: () -> MutableState<String> = remember {
        {
            viewStates.phoneNumber
        }
    }

    val onLogoutClicked = remember {
        {
            viewModel.setEvent(VerificationEvent.LogoutUser)
        }
    }
    //endregion

    //region Screen content
    VerificationScreenContent(
        onTimerClicked = onTimerClicked,
        onSubmitClicked = onSubmitClicked,
        enableTimer = { enableTimer },
        otp = otp,
        onBackPressed = onBackPressed,
        phone = phone,
        timeInSeconds = timeInSeconds,
        timeInMinutes = timeInMinutes
    ) {
        viewStates.otp.value = it
    }
    //endregion

    //region Observers
    GeneralObservers<VerificationState, VerificationViewModel>(viewModel = viewModel) {
        when (it) {
            VerificationState.OpenHome -> {

            }
            is VerificationState.OpenSetupProfile -> {
                navigator.navigate(SetupProfileAuthScreenDestination())
            }

            VerificationState.OpenLogin -> {
                navigator.navigate(
                    direction = SignInScreenDestination)
            }
            VerificationState.ShowLogoutDialog -> {
                context.showLogoutDialog(onLogoutClicked)
            }
        }
    }
    HandleLoadingStateObserver(viewModel = viewModel) {
    }
    //endregion
}


