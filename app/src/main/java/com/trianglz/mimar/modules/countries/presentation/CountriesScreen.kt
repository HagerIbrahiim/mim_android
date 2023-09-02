package com.trianglz.mimar.modules.countries.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.mimar.common.presentation.navigation.AuthGraph
import com.trianglz.mimar.modules.countries.presentation.composables.CountriesScreenContent
import com.trianglz.mimar.modules.countries.presentation.contract.CountriesEvent
import com.trianglz.mimar.modules.countries.presentation.contract.CountriesState
import com.trianglz.mimar.modules.countries.presentation.contract.CountriesViewState
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Destination
@AuthGraph
fun CountriesScreen(
    viewModel: CountriesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null,
    selectedCountryCode: String = "+966",
    resultNavigator: ResultBackNavigator<CountryUIModel>,

    ) {

    LaunchedEffect(key1 = true,){
        viewModel.setEvent(CountriesEvent.SaveCountryCode(selectedCountryCode))
    }
    val keyboardController = LocalSoftwareKeyboardController.current


    val viewStates = remember {
        viewModel.viewStates ?: CountriesViewState()
    }

    val searchText = remember {
        viewStates.searchText
    }

    val onBackButtonClicked = remember {
        {
            viewModel.setEvent(CountriesEvent.BackIconClicked)
        }
    }

    val onCountryClick: (Int) -> Unit = remember {
        {
            viewModel.setEvent(CountriesEvent.CountrySelected(it))
        }
    }

    val listLoadingState = remember {
        viewModel.source.loadingState
    }

    val showSearchTextFiled = remember(listLoadingState.value) {
        !listLoadingState.value.failedInitial
    }

    val showPlaceHolder = remember(listLoadingState.value) {
        !listLoadingState.value.isLoading
    }


    CountriesScreenContent(
        showSearchTextFiled = { showSearchTextFiled },
        showPlaceHolder = {showPlaceHolder},
        source = { viewModel.source },
        onCountryClick = onCountryClick ,
        searchText = { searchText },
        onBackButtonClicked = onBackButtonClicked,
    )

    GeneralObservers<CountriesState, CountriesViewModel>(viewModel = viewModel) {
        when (it) {
            CountriesState.FinishScreen -> {
                keyboardController?.hide()
                navigator?.popBackStack()
            }
            is CountriesState.SendSelectedCountryToPrevious -> {
                keyboardController?.hide()
                resultNavigator.navigateBack(result = it.country)
            }
        }
    }
}
