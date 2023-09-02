package com.trianglz.mimar.modules.change_password.presentation.contract

import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import kotlinx.coroutines.flow.MutableStateFlow

sealed class ChangePasswordEvent : BaseEvent {
    object SendClicked : ChangePasswordEvent()
    data class BackPressed(val fromHome: Boolean) : ChangePasswordEvent()
    object LogoutUser: ChangePasswordEvent()
}

sealed class ChangePasswordState : BaseState {
    object FinishScreen : ChangePasswordState()
    object OpenHome: ChangePasswordState()
    object ShowLogoutDialog : ChangePasswordState()
    object OpenLogin: ChangePasswordState()
    object OpenCompleteProfile: ChangePasswordState()

}

data class ChangePasswordViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val confirmNewPassword: TextFieldState = TextFieldState(),
    val newPassword: TextFieldState = TextFieldState(),
    val oldPassword: TextFieldState = TextFieldState(),
    var fromHome: Boolean = false,
) : BaseViewState