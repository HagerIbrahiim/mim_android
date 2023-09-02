package com.trianglz.mimar.modules.phone.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.presentation.extensions.showLogoutDialog
import com.trianglz.mimar.common.presentation.navigation.AuthGraph
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel
import com.trianglz.mimar.modules.destinations.CountriesScreenDestination
import com.trianglz.mimar.modules.destinations.SignInScreenDestination
import com.trianglz.mimar.modules.destinations.VerificationScreenDestination
import com.trianglz.mimar.modules.phone.presentation.composables.PhoneNumberScreenContent
import com.trianglz.mimar.modules.phone.presentation.contract.PhoneNumberEvent
import com.trianglz.mimar.modules.phone.presentation.contract.PhoneNumberState
import com.trianglz.mimar.modules.phone.presentation.contract.PhoneNumberViewState
import com.trianglz.mimar.modules.verification.presentation.contract.VerificationEvent

@Composable
@Destination
@AuthGraph
fun PhoneNumberScreen(
    viewModel: PhoneNumberViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    countriesScreenResult: ResultRecipient<CountriesScreenDestination, CountryUIModel>,

    ) {

    //region Members and Action
    val context = LocalContext.current

    val viewStates = remember {
        viewModel.viewStates ?: PhoneNumberViewState()
    }

    val phone = remember {
        {
            viewStates.phone
        }
    }

    val isSubmitButtonEnabled = remember {
        {
            viewStates.phone.isValid.value
        }
    }

    val onBackButtonClicked = remember {
        {
            viewModel.setEvent(PhoneNumberEvent.BackButtonClicked)
        }
    }

    val onCountryCodeClicked = remember {
        {
            viewModel.setEvent(PhoneNumberEvent.CountryCodeClicked)
        }
    }

    val onSubmitButtonClicked = remember {
        {
            viewModel.setEvent(PhoneNumberEvent.SubmitButtonClicked)
        }
    }

    val onLogoutClicked = remember {
        {
            viewModel.setEvent(PhoneNumberEvent.LogoutUser)
        }
    }

    BackHandler {
        viewModel.setEvent(PhoneNumberEvent.BackButtonClicked)
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

    //region Screen content
    PhoneNumberScreenContent(phone, isSubmitButtonEnabled, onBackButtonClicked,onCountryCodeClicked,onSubmitButtonClicked)
    //endregion

    //region Observers
    GeneralObservers<PhoneNumberState, PhoneNumberViewModel>(viewModel = viewModel) {
        when (it) {
            PhoneNumberState.FinishScreen -> {
                navigator.popBackStack()
            }
            PhoneNumberState.OpenCountriesList -> {
                navigator.navigate(CountriesScreenDestination(
                    selectedCountryCode = viewStates.phone.countryCode.value.first,
                ))
            }
            PhoneNumberState.OpenVerifyScreen -> {
                navigator.navigate(VerificationScreenDestination)
            }
            PhoneNumberState.OpenLogin -> {
                navigator.navigate(SignInScreenDestination)

            }
            PhoneNumberState.ShowLogoutDialog -> {
                context.showLogoutDialog(onLogoutClicked)
            }
        }

    }
    HandleLoadingStateObserver(viewModel = viewModel) {
    }
    //endregion
}
