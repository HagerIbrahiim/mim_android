package com.trianglz.mimar.modules.setup_profile.presentation

import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import com.trianglz.core.data.network.repository.aws.AWSFolderMode
import com.trianglz.core.di.qualifiers.DeviceId
import com.trianglz.core.domain.enum.SimpleDateFormatEnum
import com.trianglz.core.domain.extensions.formatDate
import com.trianglz.core.domain.usecase.aws.UploadAWSFileUseCase
import com.trianglz.mimar.BuildConfig
import com.trianglz.mimar.common.presentation.base.MimarBaseViewModel
import com.trianglz.mimar.modules.destinations.SetupProfileMainScreenDestination
import com.trianglz.mimar.modules.setup_profile.presentation.contract.SetupProfileEvent
import com.trianglz.mimar.modules.setup_profile.presentation.contract.SetupProfileState
import com.trianglz.mimar.modules.setup_profile.presentation.contract.SetupProfileViewState
import com.trianglz.mimar.modules.setup_profile.presentation.model.GenderRadioButtonOption
import com.trianglz.mimar.modules.setup_profile.presentation.model.getGenderRadioButtonOption
import com.trianglz.mimar.modules.user.domain.model.UpdateUserDomainModel
import com.trianglz.mimar.modules.user.domain.usecase.*
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetupProfileViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val setUserUseCase: SetUserUseCase,
    private val logOutUserUseCase: LogOutUserUseCase,
    private val awsFileUseCase: UploadAWSFileUseCase,
    private val getUserUpdatesUseCase: GetUserUpdatesUseCase,
    @DeviceId private val deviceId: String,
    private val savedStateHandle: SavedStateHandle,
    ) :
    MimarBaseViewModel<SetupProfileEvent, SetupProfileViewState, SetupProfileState>(getUserUpdatesUseCase) {

    private val fromHome get() = viewStates?.fromHome ?: false
    private val userValue get() = viewStates?.user?.value


    var isNewImage = false
    init {
        saveDataFromNavArg()
        startListenForUserUpdates()
        saveUserPhone()
    }
    override fun handleEvents(event: SetupProfileEvent) {
        when (event) {
            is SetupProfileEvent.ChangeProfilePhoto -> {
                changeImage(event.image)
            }
            SetupProfileEvent.NextClicked -> {
                setupProfile()
            }

            SetupProfileEvent.TermsAndConditionsClicked -> {
                setState { SetupProfileState.OpenTermsAndConditions }
            }
            SetupProfileEvent.SelectDateTrailingIconClicked -> {
                handleDateSelection()
            }

            SetupProfileEvent.SelectDateRowClicked ->{
                setState { SetupProfileState.OpenSelectDate }
            }
            is SetupProfileEvent.SelectedDate -> {
                selectDate(event.date)
            }
            SetupProfileEvent.BackPressed -> {
                setState { if(fromHome) SetupProfileState.ShowExitEditProfileDialog else SetupProfileState.ShowLogoutDialog }
            }
            is SetupProfileEvent.GenderChanged -> {
                setGender(event.genderRadioButtonOption)
            }
            SetupProfileEvent.LogoutUser -> {
                logoutUser()
            }
            SetupProfileEvent.ConfirmCloseScreenClicked -> {
                setState { SetupProfileState.FinishScreen }
            }

            SetupProfileEvent.DeleteImage -> {
                deleteImage()
            }
            SetupProfileEvent.TakePhotoClicked -> {
                setState { SetupProfileState.OpenCamera }
            }
            SetupProfileEvent.UploadPhotoClicked -> {
                openGallery()
            }
        }
    }

    private fun saveDataFromNavArg() {
        viewStates?.fromHome = SetupProfileMainScreenDestination.argsFrom(savedStateHandle).fromHome ?: false
    }

    private fun changeImage(image: String, isNew : Boolean = true) {
        viewStates?.profileImage?.value = image
        isNewImage = isNew

    }

    private fun saveUserPhone(){
        launchCoroutine {
            val user = getUserUseCase.execute()
            val phone = user.phoneNumber?.trimStart('0') ?:""
            viewStates?.phone?.textFieldValue?.value = TextFieldValue(phone)
            viewStates?.phone?.countryCode?.value = Pair(user.phoneDialCode ?:"", user.country?.shortCode ?:"sa")
        }
    }
    private fun selectDate(date: String) {
        viewStates?.dateOfBirth?.textFieldValue?.value =
            TextFieldValue(text = date.formatDate(
                SimpleDateFormatEnum.DAY_MONTH_YEAR5,
                SimpleDateFormatEnum.DAY_MONTH_YEAR4
            ) ?:"")
        viewStates?.dateOfBirth?.isValid?.value = true
    }

    private fun setGender(genderRadioButtonOption: GenderRadioButtonOption) {
        viewStates?.selectedGender?.value = if(viewStates?.selectedGender?.value != genderRadioButtonOption) genderRadioButtonOption
        else null
    }

    override fun createInitialViewState(): SetupProfileViewState {
        return SetupProfileViewState()
    }

    private fun setupProfile() {
        val firstName = viewStates?.firstName?.textFieldValue?.value?.text ?: return
        val lastName = viewStates?.lastName?.textFieldValue?.value?.text ?: return
        val dob = viewStates?.dateOfBirth?.textFieldValue?.value?.text
        val phoneNumber = viewStates?.phone?.textFieldValue?.value?.text ?: return
        val email = viewStates?.email?.textFieldValue?.value?.text
        val image = viewStates?.profileImage?.value
        val gender = viewStates?.selectedGender?.value?.serverName ?: if (fromHome) "" else null
        val dialCode = viewStates?.phone?.countryCode?.value?.first ?: ""

        launchCoroutine {
            setLoading()
            val fileName = if (isNewImage) awsFileUseCase.execute(
                BuildConfig.BUCKET_NAME,
                image?.toUri(),
                AWSFolderMode.USERS.value
            ) else image

            fileName?.let { changeImage(it, false) }

            val userDomainModel = UpdateUserDomainModel(
                firstName = firstName,
                lastName = lastName,
                dateOfBirth = if (!dob.isNullOrEmpty()) dob else "",
                image = fileName,
                email = if (!email.isNullOrEmpty()) email else null,
                phoneNumber = phoneNumber,
                gender = gender,
                dialCode = dialCode,
            )
            val customer = updateUserUseCase.execute(userDomainModel)
            setUserUseCase.execute(customer)
            setDoneLoading()
            setState { if(fromHome) SetupProfileState.FinishScreen else SetupProfileState.OpenAddresses }
        }

    }

    private fun deleteImage() {
        viewStates?.profileImage?.value = ""
        isNewImage = false
    }

    private fun logoutUser(){
        launchCoroutine {
            setLoading()
            logOutUserUseCase.execute(deviceId)
            setDoneLoading()
            setState { SetupProfileState.OpenLogin }
        }
    }

    override fun userUpdates(user: UserUIModel) {
        viewStates?.user?.value = user
        user.email?.let {
            viewStates?.email?.textFieldValue?.value = TextFieldValue(it)
        }
        user.firstName?.let{
            viewStates?.firstName?.textFieldValue?.value = TextFieldValue(it)
        }
        user.lastName?.let{
            viewStates?.lastName?.textFieldValue?.value = TextFieldValue(it)
        }
        user.image?.let {
            viewStates?.profileImage?.value = it
        }
        user.phoneNumber?.let {
            viewStates?.phone?.textFieldValue?.value = TextFieldValue(it)
        }
        user.country?.shortCode?.let {
            viewStates?.phone?.countryCode?.value = Pair(user.phoneDialCode ?:"", it)

        }
        user.gender?.let {
            viewStates?.selectedGender?.value = it.getGenderRadioButtonOption()
        }

        user.dateOfBirth?.let {
            viewStates?.dateOfBirth?.textFieldValue?.value =
                TextFieldValue(text = it.formatDate(
                    SimpleDateFormatEnum.DATE_INPUT2,
                    SimpleDateFormatEnum.DAY_MONTH_YEAR4
                ) ?:"")
        }
    }

    private fun handleDateSelection(){
        if(viewStates?.dateOfBirth?.textFieldValue?.value?.text.isNullOrEmpty())
            setState { SetupProfileState.OpenSelectDate }
        else {
            viewStates?.dateOfBirth?.textFieldValue?.value =  TextFieldValue(text ="")
        }
    }
}