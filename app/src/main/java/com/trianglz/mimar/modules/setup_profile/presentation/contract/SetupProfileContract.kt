package com.trianglz.mimar.modules.setup_profile.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.setup_profile.presentation.model.GenderRadioButtonOption
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import kotlinx.coroutines.flow.MutableStateFlow

sealed class SetupProfileEvent : BaseEvent {
    object TermsAndConditionsClicked : SetupProfileEvent()
    object NextClicked : SetupProfileEvent()
    object SelectDateRowClicked : SetupProfileEvent()
    object SelectDateTrailingIconClicked : SetupProfileEvent()

    object BackPressed : SetupProfileEvent()
    object LogoutUser: SetupProfileEvent()
    object ConfirmCloseScreenClicked: SetupProfileEvent()
    object DeleteImage: SetupProfileEvent()
    object UploadPhotoClicked : SetupProfileEvent()
    object TakePhotoClicked : SetupProfileEvent()
    data class ChangeProfilePhoto(val image: String) : SetupProfileEvent()
    data class SelectedDate(val date: String) : SetupProfileEvent()
    class GenderChanged(val genderRadioButtonOption: GenderRadioButtonOption) : SetupProfileEvent()

}

sealed class SetupProfileState : BaseState {
    object OpenTermsAndConditions : SetupProfileState()
    //object OpenSelectPhoto : SetupProfileState()
    object OpenSelectDate : SetupProfileState()
    object ShowLogoutDialog : SetupProfileState()
    object OpenLogin : SetupProfileState()
    object OpenAddresses : SetupProfileState()
    object FinishScreen: SetupProfileState()
    object ShowExitEditProfileDialog: SetupProfileState()
    object OpenCamera: SetupProfileState()

}

data class SetupProfileViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val firstName: TextFieldState = TextFieldState(),
    val genderOptionList: List<GenderRadioButtonOption> =
        listOf(
            GenderRadioButtonOption.Female, GenderRadioButtonOption.Male
        ),
    val selectedGender: MutableState<GenderRadioButtonOption?> = mutableStateOf(null),
    val lastName: TextFieldState = TextFieldState(),
    val email: TextFieldState = TextFieldState(),
    val dateOfBirth: TextFieldState = TextFieldState(),
    val phone: TextFieldState = TextFieldState(),
    val profileImage: MutableState<String?> = mutableStateOf(null),
    val isConsumed: MutableState<Boolean> = mutableStateOf(false),
    var user: MutableState<UserUIModel?> = mutableStateOf(null),
    var fromHome: Boolean = false,

    ) : BaseViewState