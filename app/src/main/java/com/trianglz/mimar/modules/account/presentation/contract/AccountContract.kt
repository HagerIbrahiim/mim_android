package com.trianglz.mimar.modules.account.presentation.contract


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.account.presentation.model.BaseSettingModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import kotlinx.coroutines.flow.MutableStateFlow


/// Events that user performed
sealed class AccountEvent : BaseEvent {
    object EditProfileClicked : AccountEvent()
    object AddressesClicked : AccountEvent()
    object ChangeLanguageClicked: AccountEvent()
    object ChangePasswordClicked : AccountEvent()
    object DeleteAccountClicked: AccountEvent()
    object LogoutClicked: AccountEvent()
    object ConfirmLoggingOutClicked: AccountEvent()
    object ConfirmDeleteAccountClicked: AccountEvent()

    data class ToggleNotifications(val enabled: Boolean) : AccountEvent()
    data class ContactViaPhoneClicked(val phone: String) : AccountEvent()
    data class ContactViaMailClicked(val mail: String): AccountEvent()

}

sealed class AccountState : BaseState {
    object OpenEditProfile : AccountState()
    object OpenAddresses : AccountState()
    object ShowLanguageDialog: AccountState()
    object OpenChangePassword : AccountState()
    object ShowDeleteAccountDialog: AccountState()
    object ShowLogoutDialog: AccountState()
    object OpenLogin : AccountState()
    object OpenHome : AccountState()
    data class OpenContactViaPhone(val phone: String) : AccountState()
    data class OpenContactViaMail(val mail: String) : AccountState()

}

data class AccountViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var user: MutableState<UserUIModel?> = mutableStateOf(null),
    val list: SnapshotStateList<BaseSettingModel> = SnapshotStateList(),
    ) : BaseViewState