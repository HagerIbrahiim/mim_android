package com.trianglz.mimar.modules.change_password.presentation

import androidx.lifecycle.SavedStateHandle
import com.trianglz.core.di.qualifiers.DeviceId
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.model.AsyncState
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.change_password.domain.model.ChangePasswordDomainModel
import com.trianglz.mimar.modules.change_password.domain.usecase.ChangePasswordUseCase
import com.trianglz.mimar.modules.change_password.presentation.contract.ChangePasswordEvent
import com.trianglz.mimar.modules.change_password.presentation.contract.ChangePasswordState
import com.trianglz.mimar.modules.change_password.presentation.contract.ChangePasswordViewState
import com.trianglz.mimar.modules.destinations.ChangePasswordMainScreenDestination
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel
import com.trianglz.mimar.modules.user.domain.usecase.LogOutUserUseCase
import com.trianglz.mimar.modules.user.domain.usecase.SetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val setUserUseCase: SetUserUseCase,
    private val logOutUserUseCase: LogOutUserUseCase,
    @DeviceId private val deviceId: String,
    private val savedStateHandle: SavedStateHandle,
    ) : BaseMVIViewModel<ChangePasswordEvent, ChangePasswordViewState, ChangePasswordState>() {

    init {
        saveDataFromNavArg()
    }
    override fun handleEvents(event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.BackPressed -> {
                setState {
                    if (event.fromHome)
                        ChangePasswordState.FinishScreen
                    else ChangePasswordState.ShowLogoutDialog
                }
            }
            ChangePasswordEvent.SendClicked -> {
                changePassword()
            }
            ChangePasswordEvent.LogoutUser -> {
                logoutUser()
            }

        }
    }

    private fun saveDataFromNavArg() {
       viewStates?.fromHome = ChangePasswordMainScreenDestination.argsFrom(savedStateHandle).fromHome ?: false
    }

    override fun createInitialViewState(): ChangePasswordViewState {
        return ChangePasswordViewState()
    }

    private fun changePassword() {
        val newPassword = viewStates?.newPassword?.textFieldValue?.value?.text ?: return
        val oldPassword = viewStates?.oldPassword?.textFieldValue?.value?.text ?: return
        launchCoroutine {
            setLoading()
            val user = changePasswordUseCase.execute(ChangePasswordDomainModel(newPassword, oldPassword))
            setUserUseCase.execute(user)
            setDoneLoading()
            _loadingState.postValue(AsyncState.SuccessWithMessage(StringWrapper(R.string.password_successfully_changed)))
            setupNavigation(user)
        }

    }

    private fun setupNavigation(user: UserDomainModel) {
        if (user.isPhoneVerified == true && user.isProfileCompleted == false)
            setState { ChangePasswordState.OpenCompleteProfile }
        else if (user.isProfileCompleted == true)
            setState {
                if (viewStates?.fromHome == true) ChangePasswordState.FinishScreen else
                    ChangePasswordState.OpenHome
            }
    }

    private fun logoutUser(){
        launchCoroutine {
            setLoading()
            logOutUserUseCase.execute(deviceId)
            setDoneLoading()
            setState { ChangePasswordState.OpenLogin }
        }
    }

}
