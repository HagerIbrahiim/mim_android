package com.trianglz.mimar.modules.search.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.trianglz.core.domain.helper.debounce
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.pagination.model.ComposePaginationModel
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.base.GeneralUpdatesViewModel
import com.trianglz.mimar.common.presentation.tabs.models.TabItemUIModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchFavouritesUpdates
import com.trianglz.mimar.modules.branches.presentation.mapper.toDomainModel
import com.trianglz.mimar.modules.branches.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.branches.presentation.source.BranchesSource
import com.trianglz.mimar.modules.destinations.SearchScreenDestination
import com.trianglz.mimar.modules.search.presentation.contract.SearchEvent
import com.trianglz.mimar.modules.search.presentation.contract.SearchState
import com.trianglz.mimar.modules.search.presentation.contract.SearchViewState
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.services.presentation.mapper.toDomain
import com.trianglz.mimar.modules.services.presentation.model.ServiceType
import com.trianglz.mimar.modules.services.presentation.source.ServicesListSource
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val servicesSource: ServicesListSource,
    getBranchFavouritesUpdates: GetBranchFavouritesUpdates,
    val branchesSource: BranchesSource,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    private val savedStateHandle: SavedStateHandle,

    ) : GeneralUpdatesViewModel<SearchEvent, SearchViewState, SearchState>(getUserUpdatesUseCase, getBranchFavouritesUpdates) {


//    val user = getUserUpdatesUseCase.execute().map { it.toUIModel() }
    private val location get() = viewStates?.location?.value
    private val searchText get() = viewStates?.searchText?.value ?:""



    val onSearch = debounce(500L, viewModelScope) { searchingString: String ->
        if((searchText.length) >= 3) {
                viewStates?.searchText?.value = searchingString
                getData()
        }
        else{
            branchesSource.dataListValue?.clear()
            servicesSource.dataListValue?.clear()
        }
    }


    init {
        saveNavArgData()
        handleTabs()
        observeSearchStringChanges()
        fillSourceData()
    }

    private fun fillSourceData() {
        branchesSource.apply {
            onFavoriteClick = {
                setEvent(SearchEvent.ToggleBranchFavoriteClicked(it))
            }

            branchesSource.onClick = {
                setEvent(SearchEvent.ItemBranchClicked(it))
            }

            servicesSource.apply {
                serviceType = ServiceType.SearchService
                onServiceItemClicked = {
                    setEvent(SearchEvent.ServiceItemClicked(it))
                }
                onAddServiceToCartClicked = {
                    setEvent(SearchEvent.AddServiceToCart(it))
                }
            }
        }
    }

    private fun handleTabs() {
        viewStates?.tabList?.addAll(
            listOf(
                TabItemUIModel(StringWrapper(R.string.providers), R.drawable.branch_icon, mutableStateOf(true)),
                TabItemUIModel(StringWrapper(R.string.services), R.drawable.services_icon, mutableStateOf(false)),
            )
        )
    }

    override fun handleEvents(event: SearchEvent) {
        when (event) {
            is SearchEvent.TabChanged -> {
                handleChangedTab(event.index)

            }
            is SearchEvent.ItemBranchClicked -> {
                setState { SearchState.OpenBranchDetails(event.id) }
            }
            is SearchEvent.ToggleBranchFavoriteClicked -> {
                toggleUpdatableItem(event.item.toDomainModel(), event.item.isFavorite)
            }
            SearchEvent.BackIconClicked -> {
                setState { SearchState.FinishScreen}

            }
            is SearchEvent.AddServiceToCart -> {
                toggleUpdatableItem(event.item.toDomain(), event.item.isAdded.value)
            }
            is SearchEvent.ServiceItemClicked -> {
                val service= event.service
                val serviceBranch = service.branch
                serviceBranch?.let {
                    Log.d("TAG", "handleEvents: 111 search ${service.branchSpecialtyId}")
                    setState { SearchState.OpenBranchDetails(it.id, service.branchSpecialtyId ) }
                }
            }
        }
    }

    private fun saveNavArgData(){
       viewStates?.location?.value = SearchScreenDestination.argsFrom(savedStateHandle).location
    }

    private fun handleChangedTab(index: Int) {
        viewStates?.tabList?.find { it.isSelected.value }?.isSelected?.value = false
        viewStates?.tabList?.getOrNull(index)?.isSelected?.value = true
        viewStates?.showProvidersTab?.value = index == 0
        viewStates?.scrollToFirstPosition?.value = true
        getData()
    }

    private fun observeSearchStringChanges() {
        viewModelScope.launch(Dispatchers.IO) {
            snapshotFlow { viewStates?.searchText?.value }
                .onEach {
                    it?.let {
                        onSearch(it)
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun getData() {

        if (viewStates?.tabList?.indexOfFirst { it.isSelected.value } == 0) {
            branchesSource.searchString = searchText
            branchesSource.showCategories = false

            branchesSource.lat = location?.latitude
            branchesSource.lng = location?.longitude
            branchesSource.refreshAll()

        } else {
            servicesSource.searchString = searchText
            servicesSource.refreshAll()
        }

    }

    override fun updateItem(item: BaseUpdatableItem) {
        when (item) {
            is BranchDomainModel -> {
                val list = branchesSource.getCurrentList()
                val updatedBranch =
                    item.toUIModel(onFavoriteClick = { branch ->
                        setEvent(SearchEvent.ToggleBranchFavoriteClicked(branch))
                    }) { id ->
                        setEvent(SearchEvent.ItemBranchClicked(id))
                    }
                val currentBranch = list.find { it.uniqueId == updatedBranch.id }
                val currentIndex = list.indexOf(currentBranch)
                if (currentIndex != -1) {
                    branchesSource.updateItem(
                        ComposePaginationModel.UpdateAction.Update(),
                        updatedBranch, currentIndex,
                    )
                }
            }

            is ServiceDomainModel -> {
                val list = servicesSource.getCurrentList()
                val currentSession = list.find { it.uniqueId == item.uniqueId }
                currentSession?.isAdded?.value = item.isAdded ?: false
            }
        }
    }

    override fun createInitialViewState(): SearchViewState {
        return SearchViewState()
    }

}
