package com.trianglz.mimar.modules.sign_up.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.trianglz.core.presentation.extensions.toActivityAsNewTaskWithParams
import com.trianglz.core.presentation.extensions.toast
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.presentation.navigation.AuthGraph
import com.trianglz.mimar.modules.addresses_list.presentation.contract.AddressesListEvent
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel
import com.trianglz.mimar.modules.destinations.CountriesScreenDestination
import com.trianglz.mimar.modules.destinations.SignInScreenDestination
import com.trianglz.mimar.modules.destinations.VerificationScreenDestination
import com.trianglz.mimar.modules.home.presentation.HomeActivity
import com.trianglz.mimar.modules.sign_up.presentation.composables.SignUpScreenContent
import com.trianglz.mimar.modules.sign_up.presentation.contract.SignUpEvent
import com.trianglz.mimar.modules.sign_up.presentation.contract.SignUpState
import com.trianglz.mimar.modules.sign_up.presentation.contract.SignUpViewState

@Composable
@Destination
@AuthGraph
fun SignUpScreen(
    navigator: DestinationsNavigator,
    viewModel: SignUpViewModel = hiltViewModel(),
    countriesScreenResult: ResultRecipient<CountriesScreenDestination, CountryUIModel>,

    ) {

    //region Members and actions

    val viewStates = remember {
        viewModel.viewStates ?: SignUpViewState()
    }

    val context = LocalContext.current

    val phone = remember {
        {
            viewStates.phone
        }
    }

    val password = remember {
        {
            viewStates.password
        }
    }

    val confirmPassword = remember {
        {
            viewStates.confirmPassword
        }
    }

    val nextClickAction = remember {
        {
            viewModel.setEvent(SignUpEvent.NextClicked)
        }
    }
    val onTermsAndConditionsClicked = remember {
        {
            viewModel.setEvent(SignUpEvent.TermsAndConditionsClicked)
        }
    }

    val onBackPressed = remember {
        {
            viewModel.setEvent(SignUpEvent.BackPresses)
        }
    }

    val hideIdenticalPasswordMessage = remember {
        {
            if (password().textFieldValue.value.text.isEmpty() || confirmPassword().textFieldValue.value.text.isEmpty()) {
                true
            } else {
                if (password().textFieldValue.value.text.isNotEmpty()) {
                    confirmPassword().isValid.value =
                        password().textFieldValue.value.text == confirmPassword().textFieldValue.value.text

                    confirmPassword().isUserChangedText.value = true
                }
                password().isValid.value && confirmPassword().isValid.value
            }
        }
    }

    val onCountryCodeClicked = remember {
        {
            viewModel.setEvent(SignUpEvent.CountryCodeClicked)
        }
    }

    val onSkipButtonClicked = remember {
        {
            viewModel.setEvent(SignUpEvent.SkipButtonClicked)
        }
    }

    val onSignInClicked = remember {
        {
            viewModel.setEvent(SignUpEvent.SignInClicked)
        }
    }

    countriesScreenResult.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }
            is NavResult.Value -> {
                viewModel.viewStates?.phone?.countryCode?.value = Pair(
                    result.value.dialCode,  result.value.shortCode)
            }
        }
    }

    //endregion

    BackHandler {
        viewModel.setEvent(SignUpEvent.BackPresses)
    }

    //region Content
    SignUpScreenContent(
        phone,
        password,
        confirmPassword,
        nextClickAction,hideIdenticalPasswordMessage,
        onTermsAndConditionsClicked,onSkipButtonClicked, onBackPressed, onCountryCodeClicked,onSignInClicked
    )

    //endregion

    //region Observers

    GeneralObservers<SignUpState, SignUpViewModel>(viewModel = viewModel) {
        when (it) {
            SignUpState.OpenVerification -> {
                navigator.navigate(VerificationScreenDestination)
            }
            SignUpState.OpenTermsAndConditions -> {
                context.toast("Terms and conditions clicked")
            }
            SignUpState.BackPressed -> {
                if (!navigator.popBackStack()) {
                    navigator.navigate(SignInScreenDestination)
                }
            }
            SignUpState.OpenCountriesList -> {
                navigator.navigate(CountriesScreenDestination(
                    selectedCountryCode = viewStates.phone.countryCode.value.first,
                ))
            }
            SignUpState.OpenHome -> {
                context.toActivityAsNewTaskWithParams<HomeActivity>()
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }

    //endregion
}