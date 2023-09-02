package com.trianglz.mimar.modules.favourites.presentation

import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.mimar.common.presentation.base.GeneralUpdatesViewModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchFavouritesUpdates
import com.trianglz.mimar.modules.branches.presentation.mapper.toDomainModel
import com.trianglz.mimar.modules.favourites.presentation.contract.FavouritesEvent
import com.trianglz.mimar.modules.favourites.presentation.contract.FavouritesState
import com.trianglz.mimar.modules.favourites.presentation.contract.FavouritesViewState
import com.trianglz.mimar.modules.favourites.presentation.source.FavouritesSource
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.user.presentaion.mapper.toUIModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    getBranchFavouritesUpdates: GetBranchFavouritesUpdates,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    val source: FavouritesSource,
    val userModeHandler: UserModeHandler,
) : GeneralUpdatesViewModel<FavouritesEvent, FavouritesViewState, FavouritesState>(
    getUserUpdatesUseCase,
    getBranchFavouritesUpdates
) {

//    val user = getUserUpdatesUseCase.execute().map { it.toUIModel() }


    override fun handleEvents(event: FavouritesEvent) {
        when (event) {
            is FavouritesEvent.ItemBranchClicked ->{
                setState { FavouritesState.OpenBranchDetails(event.id) }
            }
            is FavouritesEvent.ToggleBranchFavoriteClicked -> {
                toggleUpdatableItem(event.item.toDomainModel(), event.item.isFavorite)

            }
            FavouritesEvent.OnChangeAddressClicked -> {
                setState { FavouritesState.OpenLocationBottomSheet }

            }

            FavouritesEvent.RefreshBranches -> {
                getBranches()
            }

            FavouritesEvent.RefreshAllScreen -> {
                resetViewStates()
                getData()
            }
            FavouritesEvent.OnDiscoverClicked -> {
                setState { FavouritesState.OpenDiscoverScreen }

            }
            FavouritesEvent.BottomSheetAddNewAddressClicked -> {
                setState { FavouritesState.OpenAddAddressScreen }
            }
            FavouritesEvent.CloseLocationBottomSheetClicked -> {
                setState { FavouritesState.HideLocationBottomSheet }
            }
        }
    }


    init {
        startListenForUserUpdates()
        fillSourceData()
        getData()
    }

    private fun getData() {
//        getUserData()
        getBranches()
    }

//    private fun getUserData() {
//        launchCoroutine {
//            user.collect {
//                viewStates?.selectedLocationId?.value = it.defaultAddress?.id
//                viewStates?.user?.value = it
//            }
//        }
//    }



    private fun getBranches() {
        source.refreshAll()
    }


    private fun fillSourceData() {
        source.onFavoriteClick = {
            setEvent(FavouritesEvent.ToggleBranchFavoriteClicked(it))
        }
        source.onClick = { id ->
            setEvent(FavouritesEvent.ItemBranchClicked(id))
        }
    }

    override fun updateItem(item: BaseUpdatableItem) {
        when (item) {
            is BranchDomainModel -> {
                getBranches()
            }
        }
    }

    override fun createInitialViewState(): FavouritesViewState {
        return FavouritesViewState()
    }

    override fun userUpdates(user: UserUIModel) {
        super.userUpdates(user)
        viewStates?.selectedLocationId?.value = user.defaultAddress?.id
        viewStates?.user?.value = user
    }

}
