package com.trianglz.mimar.modules.forgot_password.presentation

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
import com.trianglz.mimar.common.presentation.navigation.AuthGraph
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel
import com.trianglz.mimar.modules.destinations.CountriesScreenDestination
import com.trianglz.mimar.modules.forgot_password.presentation.composables.ForgotPasswordContent
import com.trianglz.mimar.modules.forgot_password.presentation.contract.ForgotPasswordEvent
import com.trianglz.mimar.modules.forgot_password.presentation.contract.ForgotPasswordState
import com.trianglz.mimar.modules.forgot_password.presentation.contract.ForgotPasswordViewState
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInEvent

@Composable
@Destination
@AuthGraph
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    countriesScreenResult: ResultRecipient<CountriesScreenDestination, CountryUIModel>,

    ) {

    //region Members and Action
    val context = LocalContext.current

    val viewStates = remember {
        viewModel.viewStates ?: ForgotPasswordViewState()
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
            viewModel.setEvent(ForgotPasswordEvent.BackButtonClicked)
        }
    }

    val onCountryCodeClicked = remember {
        {
            viewModel.setEvent(ForgotPasswordEvent.CountryCodeClicked)
        }
    }

    val onSubmitButtonClicked = remember {
        {
            viewModel.setEvent(ForgotPasswordEvent.SubmitButtonClicked)
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

    //region Screen content
    ForgotPasswordContent(phone, isSubmitButtonEnabled, onBackButtonClicked,onCountryCodeClicked,onSubmitButtonClicked)
    //endregion

    //region Observers
    GeneralObservers<ForgotPasswordState, ForgotPasswordViewModel>(viewModel = viewModel) {
        when (it) {
            ForgotPasswordState.FinishScreen -> {
                navigator.popBackStack()
            }
            ForgotPasswordState.OpenCountriesList -> {
                navigator.navigate(CountriesScreenDestination(
                    selectedCountryCode = viewStates.phone.countryCode.value.first,
                ))
            }
        }

    }
    HandleLoadingStateObserver(viewModel = viewModel) {
    }
    //endregion
}
