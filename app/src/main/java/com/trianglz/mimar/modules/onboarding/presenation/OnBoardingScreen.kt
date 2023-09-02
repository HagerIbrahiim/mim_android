package com.trianglz.mimar.modules.onboarding.presenation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.extensions.getActivity
import com.trianglz.core.presentation.extensions.toActivityAsNewTask
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.extensions.showLanguageDialog
import com.trianglz.mimar.common.presentation.navigation.AuthGraph
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationActivity
import com.trianglz.mimar.modules.destinations.SignInScreenDestination
import com.trianglz.mimar.modules.home.presentation.HomeActivity
import com.trianglz.mimar.modules.onboarding.presenation.composables.OnBoardingScreenContent
import com.trianglz.mimar.modules.onboarding.presenation.contract.OnBoardingEvent
import com.trianglz.mimar.modules.onboarding.presenation.contract.OnBoardingState
import com.trianglz.mimar.modules.onboarding.presenation.model.OnBoardingData
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@AuthGraph(start = true)
@Composable
@Preview
@Destination
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null
) {

    val context = LocalContext.current
    val activity = context.getActivity<AuthenticationActivity>()


    val scope = rememberCoroutineScope()
    val state = rememberPagerState()
    val items = remember {
        listOf(OnBoardingData.First,OnBoardingData.Second,OnBoardingData.Third)
    }

    val showSkipBtn = remember {
        viewModel.viewStates?.showSkipButton
    }

    LaunchedEffect(key1 = state.currentPage){
        showSkipBtn?.value = state.currentPage != items.size -1
    }

    val onNextButtonClicked = remember {
        {
            if (state.currentPage + 1 < items.size)
                viewModel.setEvent(OnBoardingEvent.ScrollToNextPage)
            else
                viewModel.setEvent(OnBoardingEvent.OnBoardingViewed)
        }
    }

    val onSkipButtonClicked = remember {
        {
            viewModel.setEvent(OnBoardingEvent.SkipButtonClicked)
        }
    }
    val currentLocaleText : @Composable () -> String = remember {
        {
            val currentLocale = (activity as AuthenticationActivity).currentLocale
            if (currentLocale == Locales.ENGLISH) stringResource(id = R.string.arabic_word)
            else Locales.ENGLISH.code.uppercase()
        }
    }
    val changeLanguageClicked = remember {
        { viewModel.setEvent(OnBoardingEvent.ChangeLanguageClicked) }
    }

    OnBoardingScreenContent(
        { showSkipBtn?.value ?: true },  { items }, { state }, currentLocaleText,changeLanguageClicked,onNextButtonClicked, onSkipButtonClicked,
    )

    GeneralObservers<OnBoardingState, OnBoardingViewModel>(viewModel = viewModel) {
        when (it) {
            OnBoardingState.OpenLogin -> {
                navigator?.navigate(SignInScreenDestination())
            }

            OnBoardingState.ScrollToNextPage -> {
                scope.launch {
                    state.scrollToPage(state.currentPage + 1)
                }
            }
            OnBoardingState.OpenHome -> {
                activity?.toActivityAsNewTask<HomeActivity>()
            }

            OnBoardingState.ShowLanguageDialog -> {
                (activity as AuthenticationActivity).apply {
                    val otherLang = Locales.values().find { it.code != currentLocale.code }
                        ?: Locales.ENGLISH
                    context.showLanguageDialog(otherLang){
                        updateLocale(otherLang)
                    }
                }
            }
        }

    }

}

