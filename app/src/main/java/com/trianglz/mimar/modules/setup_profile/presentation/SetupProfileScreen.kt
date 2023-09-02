package com.trianglz.mimar.modules.setup_profile.presentation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.domain.enum.SimpleDateFormatEnum
import com.trianglz.core.presentation.base.BaseActivity
import com.trianglz.core.presentation.extensions.*
import com.trianglz.core_compose.presentation.composables.BottomSheetContainerLayout
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.core_compose.presentation.helper.SelectImageHelper
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.extensions.checkCameraPermissions
import com.trianglz.mimar.common.presentation.extensions.openDatePicker
import com.trianglz.mimar.common.presentation.extensions.showLogoutDialog
import com.trianglz.mimar.common.presentation.fileProvider.ComposeFileProvider
import com.trianglz.mimar.modules.destinations.AddressesListAuthDestination
import com.trianglz.mimar.modules.destinations.SignInScreenDestination
import com.trianglz.mimar.modules.setup_profile.presentation.composables.EditProfileBottomSheetContent
import com.trianglz.mimar.modules.setup_profile.presentation.composables.SetupScreenContent
import com.trianglz.mimar.modules.setup_profile.presentation.contract.SetupProfileEvent
import com.trianglz.mimar.modules.setup_profile.presentation.contract.SetupProfileState
import com.trianglz.mimar.modules.setup_profile.presentation.contract.SetupProfileViewState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SetupProfileScreen(
    navigator: DestinationsNavigator,
    viewModel: SetupProfileViewModel = hiltViewModel(),
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
    val viewStates = remember {
        viewModel.viewStates ?: SetupProfileViewState()
    }
    val context = LocalContext.current

    BackHandler {
        viewModel.setEvent(SetupProfileEvent.BackPressed)
    }

    val firstName = remember {
        {
            viewStates.firstName
        }
    }

    val lastName = remember {
        {
            viewStates.lastName
        }
    }
    val phone = remember {
        {
            viewStates.phone
        }
    }

    val email = remember {
        {
            viewStates.email
        }
    }

    val dob = remember {
        {
            viewStates.dateOfBirth
        }
    }

    val profileImage = remember {
        {
            viewStates.profileImage
        }
    }

    val isButtonValid = remember(
        firstName().textFieldValue.value,
        lastName().isValid.value,
        email().isValid.value
    ) {
        {
            viewStates.firstName.isValid.value
                    && viewStates.lastName.isValid.value
                    && viewStates.phone.isValid.value
                    && viewStates.email.isValid.value
        }
    }

    val fromHome = remember {
        viewStates.fromHome
    }

    val keyboardController = LocalSoftwareKeyboardController.current


    val screenTitle = remember {
        if (fromHome) R.string.edit_profile else R.string.setup_profile
    }

    val primaryBtnText = remember {
        if (fromHome) R.string.save else R.string.submit
    }
    val secondaryBtnText = remember {
        if (fromHome) R.string.cancel else null
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()

    val onBackPressed : () -> Unit = remember {
        {
            if (sheetState.isVisible) {
                coroutineScope.launch {
                    sheetState.hide()
                }
            } else {
                viewModel.setEvent(SetupProfileEvent.BackPressed)
            }
        }
    }


    val onSelectDateClicked = remember {
        {
            viewModel.setEvent(SetupProfileEvent.SelectDateRowClicked)
        }
    }

    val onSelectDateTrailingIconClicked = remember {
        {
            viewModel.setEvent(SetupProfileEvent.SelectDateTrailingIconClicked)
        }
    }

    val onNextClicked = remember {
        {
            viewModel.setEvent(SetupProfileEvent.NextClicked)
        }
    }

    val onTermsAndConditionsClicked = remember {
        {
            viewModel.setEvent(SetupProfileEvent.TermsAndConditionsClicked)
        }
    }

    val onLogoutClicked = remember {
        {
            viewModel.setEvent(SetupProfileEvent.LogoutUser)
        }
    }

    val onUploadPhotoClicked: () -> Unit = remember {
        {
            viewModel.setEvent(SetupProfileEvent.UploadPhotoClicked)
            coroutineScope.launch {
                sheetState.hide()
            }
        }
    }

    val onTakePhotoClicked: () -> Unit = remember {
        {
            viewModel.setEvent(SetupProfileEvent.TakePhotoClicked)
            coroutineScope.launch {
                sheetState.hide()
            }
        }
    }

    val onDeleteClicked: () -> Unit = remember {
        {
            viewModel.setEvent(SetupProfileEvent.DeleteImage)
            coroutineScope.launch {
                sheetState.hide()
            }
        }
    }

    val onOpenProfileBottomSheetClicked: () -> Unit = remember {
        {
            coroutineScope.launch {
                keyboardController?.hide()
                sheetState.show()
            }
        }
    }

    val pickedImageFromCameraUri = remember {
        mutableStateOf("")
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { hasPic ->
            if (hasPic)
                viewModel.setEvent(SetupProfileEvent.ChangeProfilePhoto(pickedImageFromCameraUri.value))
        }
    )


    val gendersList = remember {
        {
            viewStates.genderOptionList
        }
    }

    val selectedGender = remember {
        {
            viewStates.selectedGender
        }
    }


    //endregion
    //region Screen content
    BottomSheetContainerLayout(
        mainScreenContent = {
            SetupScreenContent(
                screenTitle,
                fromHome,
                firstName,
                lastName,
                phone,
                email,
                dob,
                isButtonValid,
                profileImage,
                secondaryBtnText,
                primaryBtnText,
                onOpenProfileBottomSheetClicked,
                onSelectDateClicked,
                onSelectDateTrailingIconClicked,
                gendersList,
                selectedGender,
                onBackPressed,
                onTermsAndConditionsClicked,
                onNextClicked
            ) {
                viewModel.setEvent(SetupProfileEvent.GenderChanged(it))
            }
        }, bottomSheetContent = {
            EditProfileBottomSheetContent(
                hasDelete = { profileImage().value.isNullOrEmpty().not() },
                onTakePhotoClicked = onTakePhotoClicked,
                onUploadClicked = onUploadPhotoClicked,
                onDeleteClicked = onDeleteClicked,
                onCloseClicked = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                })
        }, sheetState = sheetState
    )
    //endregion
    //region Observers

    SelectImageHelper(viewModel = viewModel) {
        viewModel.setEvent(SetupProfileEvent.ChangeProfilePhoto(it.toString()))
    }


    GeneralObservers<SetupProfileState, SetupProfileViewModel>(viewModel = viewModel) {
        when (it) {
            SetupProfileState.OpenTermsAndConditions -> {
                context.toast("Open terms and conditions")
            }

            SetupProfileState.OpenSelectDate -> {

                context.getActivity<BaseActivity>()?.openDatePicker(
                    format = SimpleDateFormatEnum.DAY_MONTH_YEAR5.simpleDateFormat,
                    afterToday = false
                ) { date ->
                    viewModel.setEvent(SetupProfileEvent.SelectedDate(date))
                }

            }
            SetupProfileState.ShowLogoutDialog -> {
                context.showLogoutDialog(onLogoutClicked)
            }

            SetupProfileState.OpenLogin -> {
                navigator.navigate(SignInScreenDestination)
            }
            SetupProfileState.OpenAddresses -> {
                navigator.navigate(AddressesListAuthDestination)
            }
            SetupProfileState.FinishScreen -> {
                navigator.popBackStack()
            }
            SetupProfileState.ShowExitEditProfileDialog -> {
                context.showMaterialDialog(
                    title = context.getString(R.string.exit_edit_profile),
                    content = context.getString(R.string.are_you_sure_you_want_to_exit),
                    positiveMessage = context.getString(R.string.yes),
                    negativeMessage = context.getString(R.string.cancel),
                    positive = {
                        viewModel.setEvent(SetupProfileEvent.ConfirmCloseScreenClicked)
                    },
                    negative = {},
                    isCancellable = true
                )
            }

            SetupProfileState.OpenCamera -> {
                context.getActivity<BaseActivity>()?.checkCameraPermissions {
                    val uri = ComposeFileProvider.getImageUris(context)
                    pickedImageFromCameraUri.value = uri.second.toString()
                    cameraLauncher.launch(uri.first)
                }
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }
//endregion
}