package com.trianglz.mimar.modules.authentication.presentation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.enums.MessageDialogMode
import com.trianglz.core.presentation.extensions.observeEvent
import com.trianglz.core.presentation.extensions.parcelableExtra
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.model.AsyncState
import com.trianglz.core.presentation.sociallogin.SignInWithFacebook
import com.trianglz.core.presentation.sociallogin.SignInWithGoogle
import com.trianglz.core.presentation.sociallogin.model.SocialResponseModel
import com.trianglz.core_compose.presentation.base.BaseComposeMVIActivity
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.composables.MessageDialog
import com.trianglz.mimar.modules.NavGraphs
import com.trianglz.mimar.modules.destinations.*
import com.trianglz.mimar.modules.sign_in.domain.model.SocialLoginType
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AuthenticationActivity : BaseComposeMVIActivity<AuthenticationViewModel>() {

    @Inject
    lateinit var signInWithGoogle: SignInWithGoogle

    @Inject
    lateinit var signInWithFacebook: SignInWithFacebook

    companion object {
        const val SCREEN_MODE = "screen_mode"
    }
    //region Members


    private val screenMode: AuthenticationMode? by lazy {
        intent.parcelableExtra(SCREEN_MODE) as? AuthenticationMode
    }


    //endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initObservers()
        setListeners()
        handleModes()
    }

    private fun setListeners() {

    }

    private fun initObservers() {
        observeEvent(viewModel.loadingState) {
            globalState.postValue(it)
        }

    }

    //region Private API
    private fun handleModes() {
        when (screenMode) {
            else -> {

            }
        }
    }


    //endregion
    override fun initializeViewModel() {
        initializeViewModel(AuthenticationViewModel::class.java)
    }

    @Composable
    override fun CustomLoadingDialog() {
        BaseComposeMainUIComponents.LocalMainComponent.LoadingDialog(lottieFile = R.raw.loader)
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    override fun InitializeComposeScreen() {

        val startRoute = remember {
            when (screenMode) {
                AuthenticationMode.OnBoarding->{
                    OnBoardingScreenDestination
                }
                AuthenticationMode.Login -> {
                    SignInScreenDestination
                }

                AuthenticationMode.VerifyUser -> {
                    VerificationScreenDestination
                }

                AuthenticationMode.ChangePassword -> {
                    ChangePasswordAuthScreenDestination
                }

                AuthenticationMode.CompleteProfile -> {
                    SetupProfileAuthScreenDestination
                }

                AuthenticationMode.Register -> {

                    SignUpScreenDestination
                }

                AuthenticationMode.PhoneScreen ->{
                    PhoneNumberScreenDestination
                }

                else -> {
                    SignInScreenDestination
                }

            }
        }

        DestinationsNavHost(
            navGraph = NavGraphs.authGraph,
            startRoute = startRoute,
        )


    }

    fun signInWithGoogle(onSocialResponseModelReceived: (SocialResponseModel, SocialLoginType) -> Unit) {
        signInWithGoogle.initialize { socialResponseModel, exception ->
            if (socialResponseModel != null) {
                onSocialResponseModelReceived.invoke(socialResponseModel, SocialLoginType.Google)
            } else {
                globalState.postValue(
                    AsyncState.AsyncError.MessageCodeError(StringWrapper(exception?.message.orEmpty()))
                )
            }
        }
    }

    fun signInWithFacebook(onSocialResponseModelReceived: (SocialResponseModel, SocialLoginType) -> Unit) {
        signInWithFacebook.initialize { socialResponseModel, exception ->
            if (socialResponseModel != null) {
                onSocialResponseModelReceived.invoke(socialResponseModel, SocialLoginType.Facebook)
            } else {
                globalState.postValue(
                    AsyncState.AsyncError.MessageCodeError(StringWrapper(exception?.localizedMessage.orEmpty()))
                )
            }
        }
    }
    @Composable
    override fun CustomAlertDialog(message: String, mode: MessageDialogMode) {
        val dismiss = remember {
            {
                alertDialog.dismiss()
            }
        }


        MessageDialog({ mode },{ message }, dismiss =  dismiss)

    }

}