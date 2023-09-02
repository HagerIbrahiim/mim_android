package com.trianglz.mimar.modules.countries.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.countries.presentation.model.CountryUIModel
import kotlinx.coroutines.flow.MutableStateFlow

sealed class CountriesEvent : BaseEvent {

    data class OnSearchClicked(val searchingString: String?): CountriesEvent()

    data class SaveCountryCode(val selectedCountryCode: String): CountriesEvent()

    object BackIconClicked: CountriesEvent()

    data class CountrySelected(val countryId: Int): CountriesEvent()


}

sealed class CountriesState : BaseState {
    data class SendSelectedCountryToPrevious(val country: CountryUIModel): CountriesState()
    object FinishScreen: CountriesState()


}

data class CountriesViewState constructor(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val searchText: MutableState<String?> = mutableStateOf(""),
    val selectedCountryCode: MutableState<String?> = mutableStateOf(null),

    ) : BaseViewState
