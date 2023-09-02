package com.trianglz.mimar.modules.sign_in.presentation

import com.trianglz.mimar.common.presentation.extensions.showLanguageDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.trianglz.core.presentation.base.BaseActivity
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.extensions.getActivity
import com.trianglz.core.presentation.extensions.toActivityAsNewTaskWithParams
import com.trianglz.core.presentation.extensions.toast
import com.trianglz.core.presentation.sociallogin.model.SocialResponseModel
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.navigation.AuthGraph
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationActivity
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel
import com.trianglz.mimar.modules.destinations.*
import com.trianglz.mimar.modules.home.presentation.HomeActivity
import com.trianglz.mimar.modules.sign_in.domain.model.SocialLoginType
import com.trianglz.mimar.modules.sign_in.presentation.composables.SignInScreenContent
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInEvent
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInState
import com.trianglz.mimar.modules.sign_in.presentation.contract.SignInViewState

@Composable
@Destination
@AuthGraph
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    countriesScreenResult: ResultRecipient<CountriesScreenDestination, CountryUIModel>,
) {
    //region Status Bar
    val systemUiController = rememberSystemUiController()
    val onDisposeDarkIcons = !isSystemInDarkTheme()
    val statusBarColor = remember(isSystemInDarkTheme()) {
        if (onDisposeDarkIcons) {
            Color.Transparent
        } else
            Color.White
    }


    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
        )

        onDispose {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = onDisposeDarkIcons
            )
        }
    }
    //endregion

    //region Members and Action
    val context = LocalContext.current
    val activity = context.getActivity<AuthenticationActivity>()

    val viewStates = remember {
        viewModel.viewStates ?: SignInViewState()
    }

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

    val isSignInButtonEnabled =
        remember {
            {
                viewStates.phone.isValid.value && viewStates.password.isValid.value
            }
        }

    val onForgotPasswordClicked = remember {
        {
            viewModel.setEvent(SignInEvent.ForgotPasswordClicked)
        }
    }

    val onSignInClicked = remember {
        {
            viewModel.setEvent(SignInEvent.SignInClicked)
        }
    }

    val onFacebookClicked = remember {
        {
            viewModel.setEvent(SignInEvent.FacebookClicked)
        }
    }

    val onGoogleClicked = remember {
        {
            viewModel.setEvent(SignInEvent.GoogleClicked)
        }
    }

    val onRegisterNowClicked = remember {
        {
            viewModel.setEvent(SignInEvent.RegisterNowClicked)
        }
    }

    val onSkipClicked = remember {
        {
            viewModel.setEvent(SignInEvent.SkipClicked)
        }
    }

    val onCountryCodeClicked = remember {
        {
            viewModel.setEvent(SignInEvent.CountryCodeClicked)
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

    val currentLocaleText :@Composable  () -> String = remember {
        {
            val currentLocale = (activity as AuthenticationActivity).currentLocale
            if (currentLocale == Locales.ENGLISH) stringResource(id = R.string.arabic_word)
            else Locales.ENGLISH.code.uppercase()
        }
    }

    val changeLanguageClicked = remember {
        { viewModel.setEvent(SignInEvent.ChangeLanguageClicked) }
    }

    val onSocialResponseModelReceived: (SocialResponseModel, SocialLoginType) -> Unit =
        remember {
            { socialResponseModel, socialLoginType ->
                viewModel.setEvent(SignInEvent.SocialLogin(socialResponseModel, socialLoginType))
            }
        }

    BackHandler {
        activity?.finish()
    }
    //endregion

    //region Screen content
    SignInScreenContent(
        phone, password, currentLocaleText ,isSignInButtonEnabled, onForgotPasswordClicked,
        onSignInClicked, onFacebookClicked, onGoogleClicked, onRegisterNowClicked, onSkipClicked,
        onCountryCodeClicked,
        changeLanguageClicked
    )
    //endregion

    //region Observers
    GeneralObservers<SignInState, SignInViewModel>(viewModel = viewModel) {
        when (it) {
            SignInState.OpenHome -> {
                context.toActivityAsNewTaskWithParams<HomeActivity>()

            }
            SignInState.OpenForgotPassword -> {
                navigator.navigate(ForgotPasswordScreenDestination)
            }
            SignInState.OpenSignUp -> {
                navigator.navigate(SignUpScreenDestination)
            }
            SignInState.ContinueFacebookSignIn -> {
                context.getActivity<AuthenticationActivity>()
                    ?.signInWithFacebook(onSocialResponseModelReceived)
            }

            SignInState.ContinueGoogleSignIn -> {
                context.getActivity<AuthenticationActivity>()
                    ?.signInWithGoogle(onSocialResponseModelReceived)
            }

            SignInState.OpenCountriesList -> {
                navigator.navigate(CountriesScreenDestination(
                    selectedCountryCode = viewStates.phone.countryCode.value.first,
                ))
            }
            SignInState.OpenChangePassword -> {
                navigator.navigate(ChangePasswordAuthScreenDestination(false))
            }
            SignInState.OpenCompleteProfile -> {
                navigator.navigate(SetupProfileAuthScreenDestination())
            }
            SignInState.OpenVerifyScreen -> {
                navigator.navigate(VerificationScreenDestination)
            }
            SignInState.ShowLanguageDialog -> {
                (activity as AuthenticationActivity).apply {
                    val otherLang = Locales.values().find { it.code != currentLocale.code }
                        ?: Locales.ENGLISH
                    context.showLanguageDialog(otherLang){
                        updateLocale(otherLang)
                    }
                }
            }
            SignInState.OpenPhoneScreen -> {
                navigator.navigate(PhoneNumberScreenDestination)
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }
    //endregion
}
