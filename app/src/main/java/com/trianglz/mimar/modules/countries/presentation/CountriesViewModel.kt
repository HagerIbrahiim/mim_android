package com.trianglz.mimar.modules.countries.presentation

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.trianglz.core.domain.helper.debounce
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core_compose.presentation.pagination.model.ComposePaginationModel
import com.trianglz.mimar.modules.countries.presentation.contract.CountriesEvent
import com.trianglz.mimar.modules.countries.presentation.contract.CountriesState
import com.trianglz.mimar.modules.countries.presentation.contract.CountriesViewState
import com.trianglz.mimar.modules.countries.presentation.source.CountriesListSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    val source: CountriesListSource,
) : BaseMVIViewModel<CountriesEvent, CountriesViewState, CountriesState>() {


    val onSearch = debounce(500L, viewModelScope) { searchingString: String ->
        searchCountries(searchingString)
    }

    init {
        observeCountriesListChanges()

        observeSearchStringChanges()


    }

    override fun handleEvents(event: CountriesEvent) {
        when (event) {
            CountriesEvent.BackIconClicked -> {
                setState { CountriesState.FinishScreen }
            }

            is CountriesEvent.CountrySelected -> {
                val country = source.getCurrentList().find { it.uniqueId == event.countryId }
                country?.let {
                    setState { CountriesState.SendSelectedCountryToPrevious(country) }
                }
            }

            is CountriesEvent.OnSearchClicked -> {
                onSearch(event.searchingString ?:"")
            }

            is CountriesEvent.SaveCountryCode ->{
                if(viewStates?.selectedCountryCode?.value == null){
                    viewStates?.selectedCountryCode?.value = event.selectedCountryCode
                }
            }
        }
    }
    
    private fun observeCountriesListChanges(){
        snapshotFlow { source.dataList.value }
            .onEach {
                if(!source.loadingState.value.isLoading){
                    val selectedCountryCode = viewStates?.selectedCountryCode?.value
                    val selectedCountryIndex =
                        source.getCurrentList().indexOfFirst { it.dialCode == selectedCountryCode }
                    val selectedCountryItem =
                        source.getCurrentList().find { it.dialCode == selectedCountryCode }
                    source.updateItem(
                        ComposePaginationModel.UpdateAction.Update(true),
                        selectedCountryItem?.copy(selected = true), selectedCountryIndex,
                    )
                }

            }
            .launchIn(viewModelScope)

    }

    private fun observeSearchStringChanges(){
        viewModelScope.launch(Dispatchers.IO) {
            snapshotFlow { viewStates?.searchText?.value }
                .onEach {
                    onSearch(it?.trim() ?:"")
                }
                .launchIn(viewModelScope)
        }
    }

    private fun searchCountries(searchingString: String?,) {
        if (source.searchString != searchingString) {
            source.searchString = searchingString
            getCountries()
        }
    }

    private fun getCountries(){
        source.refreshAll()
    }

    override fun createInitialViewState(): CountriesViewState {
        return CountriesViewState()
    }
}