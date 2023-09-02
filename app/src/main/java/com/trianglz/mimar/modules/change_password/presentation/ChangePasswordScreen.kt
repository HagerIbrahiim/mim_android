package com.trianglz.mimar.modules.change_password.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.presentation.extensions.toActivityAsNewTaskWithParams
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.extensions.showLogoutDialog
import com.trianglz.mimar.modules.change_password.presentation.composables.ChangePasswordContent
import com.trianglz.mimar.modules.change_password.presentation.contract.ChangePasswordEvent
import com.trianglz.mimar.modules.change_password.presentation.contract.ChangePasswordState
import com.trianglz.mimar.modules.change_password.presentation.contract.ChangePasswordViewState
import com.trianglz.mimar.modules.destinations.SetupProfileAuthScreenDestination
import com.trianglz.mimar.modules.destinations.SignInScreenDestination
import com.trianglz.mimar.modules.home.presentation.HomeActivity

@Composable
fun ChangePasswordScreen(
    navigator: DestinationsNavigator,
    viewModel: ChangePasswordViewModel = hiltViewModel(),
) {

    //region Status Bar
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )

        onDispose {}
    }
    //endregion
    //region Members and Actions

    val context = LocalContext.current

    val viewStates = remember {
        viewModel.viewStates ?: ChangePasswordViewState()
    }

    val currentPassword = remember {
        viewStates.oldPassword
    }

    val newPassword = remember {
        viewStates.newPassword
    }

    val confirmPassword = remember {
        viewStates.confirmNewPassword

    }

    val fromHome = remember {
        viewStates.fromHome
    }

    val buttonText = remember(fromHome) {
        if(fromHome) R.string.save else R.string.send
    }

    val onBackClicked = remember {
        {
            viewModel.setEvent(ChangePasswordEvent.BackPressed(viewStates.fromHome ?: false))

        }
    }

    val onLogoutClicked = remember {
        {
            viewModel.setEvent(ChangePasswordEvent.LogoutUser)
        }
    }

    val onSendClicked = remember {
        {
            viewModel.setEvent(ChangePasswordEvent.SendClicked)
        }
    }

    val isSendEnabled = remember {
        {
            newPassword.isValid.value && currentPassword.isValid.value && confirmPassword.isValid.value
        }
    }

    val hideIdenticalPasswordMessage = remember {
        {
            if (newPassword.textFieldValue.value.text.isEmpty() || confirmPassword.textFieldValue.value.text.isEmpty()) {
                true
            } else {
                if (newPassword.textFieldValue.value.text.isNotEmpty()) {
                    confirmPassword.isValid.value =
                        newPassword.textFieldValue.value.text == confirmPassword.textFieldValue.value.text

                    confirmPassword.isUserChangedText.value = true
                }
                newPassword.isValid.value && confirmPassword.isValid.value
            }
        }
    }

    //endregion

    //region Screen content
    ChangePasswordContent(
        fromHome,
        currentPassword,
        newPassword,
        confirmPassword,
        buttonText,
        isSendEnabled,
        hideIdenticalPasswordMessage,
        onBackClicked,
        onSendClicked
    )
    //endregion

    //region Observers
    GeneralObservers<ChangePasswordState, ChangePasswordViewModel>(viewModel = viewModel) {
        when (it) {
            ChangePasswordState.FinishScreen -> {
                navigator.popBackStack()
            }
            ChangePasswordState.OpenHome -> {
                context.toActivityAsNewTaskWithParams<HomeActivity>()
            }
            ChangePasswordState.ShowLogoutDialog -> {
                context.showLogoutDialog(onLogoutClicked)
            }
            ChangePasswordState.OpenLogin -> {
                navigator.navigate(SignInScreenDestination)
            }

            ChangePasswordState.OpenCompleteProfile->{
                navigator.navigate(SetupProfileAuthScreenDestination())
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }
    //endregion
}