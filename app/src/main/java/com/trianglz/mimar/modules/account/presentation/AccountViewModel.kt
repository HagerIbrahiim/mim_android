package com.trianglz.mimar.modules.account.presentation

import android.app.Application
import android.util.Log
import com.trianglz.core.di.qualifiers.DeviceId
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.base.BaseActivity
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.enums.Locales.*
import com.trianglz.core.presentation.extensions.getActivity
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.helper.getFcmTokenOrNull
import com.trianglz.core.presentation.model.AsyncState
import com.trianglz.core.presentation.utils.getAppLocale
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.base.MimarBaseViewModel
import com.trianglz.mimar.common.presentation.models.ProfileHeaderInfoModel
import com.trianglz.mimar.modules.account.presentation.contract.AccountEvent
import com.trianglz.mimar.modules.account.presentation.contract.AccountEvent.*
import com.trianglz.mimar.modules.account.presentation.contract.AccountState
import com.trianglz.mimar.modules.account.presentation.contract.AccountState.*
import com.trianglz.mimar.modules.account.presentation.contract.AccountViewState
import com.trianglz.mimar.modules.account.presentation.model.*
import com.trianglz.mimar.modules.home.presentation.HomeActivity
import com.trianglz.mimar.modules.user.domain.model.UpdateUserDomainModel
import com.trianglz.mimar.modules.user.domain.usecase.DeleteUserUseCase
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.user.domain.usecase.LogOutUserUseCase
import com.trianglz.mimar.modules.user.domain.usecase.UpdateUserUseCase
import com.trianglz.mimar.modules.user.presentaion.mapper.toUIModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getUserUpdatesUseCase: GetUserUpdatesUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val logOutUserUseCase: LogOutUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    @DeviceId private val deviceId: String,
) : MimarBaseViewModel<AccountEvent, AccountViewState, AccountState>(getUserUpdatesUseCase) {


    private val userValue get() = viewStates?.user?.value

    init {
        startListenForUserUpdates()
        getList()
    }

    override fun handleEvents(event: AccountEvent) {
        when (event) {
            EditProfileClicked ->{
                setState { OpenEditProfile }
            }
            AddressesClicked -> {
                setState { OpenAddresses }
            }
            ChangeLanguageClicked -> {
                setState { ShowLanguageDialog }
            }
            ChangePasswordClicked -> {
                setState { OpenChangePassword }
            }
            is ContactViaMailClicked -> {
                setState { OpenContactViaMail(event.mail) }
            }
            is ContactViaPhoneClicked ->{
                setState { OpenContactViaPhone(event.phone) }
            }
            DeleteAccountClicked -> {
                setState { ShowDeleteAccountDialog }
            }
            LogoutClicked -> {
                setState { ShowLogoutDialog }
            }
            is ToggleNotifications -> {
                toggleNotifications(event.enabled)
            }
            ConfirmDeleteAccountClicked -> {
                deleteAccount()
            }
            ConfirmLoggingOutClicked -> {
                logOut()
            }
        }
    }

    private fun getList(){
        val list = mutableListOf<BaseSettingModel>()

        list.add(
            ProfileHeaderInfoModel(userValue?.concatenatedName ?:"",
        userValue?.image)
        )

        // General Section
        list.add(ProfileTitleModel(R.string.general))
        list.add(ProfileMainItemModel(
            title = if(getAppLocale() == ARABIC.code)
                R.string.my_account else R.string.profile,
            icon = R.drawable.profile_icon,
            itemPosition = ProfileItemPosition.Top,
            onClick = {
                setEvent(EditProfileClicked)
            }
        ))

        list.add(ProfileMainItemModel(
            title = R.string.saved_addresses,
            icon = R.drawable.ic_location,
            itemPosition = ProfileItemPosition.Bottom,
            onClick = {
                setEvent(AddressesClicked)
            }
        ))

        // Settings Section
        list.add(ProfileTitleModel(R.string.settings))
        list.add(ProfileMainItemModel(
            title = R.string.notifications,
            icon = R.drawable.notification_icon,
            itemPosition = ProfileItemPosition.Top,
            hasSwitch = true,
            isSwitchChecked = viewStates?.user?.value?.isNotifiable,
            onClick = {
                setEvent(ToggleNotifications(it ?: false))
            },

        ))

        list.add(ProfileMainItemModel(
            title = R.string.language,
            icon = R.drawable.language_icon,
            itemPosition = ProfileItemPosition.Middle,
            subTitle = if (getAppLocale() == ARABIC.code) ARABIC.stringResName else ENGLISH.stringResName,
            onClick = {
                setEvent(ChangeLanguageClicked)
            }
        ))

        list.add(ProfileMainItemModel(
            title = R.string.change_password,
            icon = R.drawable.lock_icon,
            itemPosition = ProfileItemPosition.Middle,
            onClick = {
                setEvent(ChangePasswordClicked)
            }
        ))

        list.add(ProfileMainItemModel(
            title = R.string.delete_account,
            icon = R.drawable.delete_icon,
            itemPosition = ProfileItemPosition.Bottom,
            onClick = {
                setEvent(DeleteAccountClicked)
            }
        ))

        // General Section
        list.add(ProfileTitleModel(R.string.get_in_touch))
//        list.add(ProfileMainItemModel(
//            title = R.string.phone,
//            icon = R.drawable.call_icon,
//            itemPosition = ProfileItemPosition.Top,
//            onClick = {
//                setEvent(ContactViaPhoneClicked(ContactUsMode.SupportPhone.data))
//            }
//        ))

        list.add(ProfileMainItemModel(
            title = R.string.email,
            icon = R.drawable.ic_email,
            itemPosition = ProfileItemPosition.Bottom,
            onClick = {
                setEvent(ContactViaMailClicked(ContactUsMode.SupportEmail.data))
            }
        ))

        list.add(ProfileMainItemModel(
            title = R.string.logout,
            icon = R.drawable.logout_icon,
            itemPosition = ProfileItemPosition.Bottom,
            isSubSetting = true,
            onClick = {
                setEvent(LogoutClicked)
            }
        ))

        viewStates?.list?.clear()
        viewStates?.list?.addAll(list)

    }

    private fun toggleNotifications(enabled: Boolean) {

        launchCoroutine {
            setLoading()
            updateUserUseCase.execute(
                UpdateUserDomainModel(
                    fcmToken = getFcmTokenOrNull(),
                    deviceId = deviceId,
                    pushNotification = enabled
                )
            )
            setDoneLoading()

        }
    }

    private fun deleteAccount() {
        launchCoroutine {
            setLoading()
            deleteUserUseCase.execute()
            setDoneLoading()
            setState { OpenHome }

        }
    }

    private fun logOut() {
        launchCoroutine {
            setLoading()
            logOutUserUseCase.execute(deviceId)
            setDoneLoading()
            setState { OpenLogin }
        }
    }


    override fun userUpdates(user: UserUIModel) {
        viewStates?.user?.value = user
        if(viewStates?.list?.isNotEmpty() == true) {
            viewStates?.list?.removeAt(0)
            viewStates?.list?.add(0,ProfileHeaderInfoModel(userValue?.concatenatedName ?:"",
                userValue?.image)
            )
        }
    }

    override fun createInitialViewState(): AccountViewState {
        return AccountViewState()
    }


}
