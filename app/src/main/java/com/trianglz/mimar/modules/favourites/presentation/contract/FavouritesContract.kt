package com.trianglz.mimar.modules.favourites.presentation.contract


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import kotlinx.coroutines.flow.MutableStateFlow


/// Events that user performed
sealed class FavouritesEvent : BaseEvent {
    data class ItemBranchClicked(val id: Int): FavouritesEvent()
    data class ToggleBranchFavoriteClicked(val item: BranchUIModel): FavouritesEvent()
    object RefreshAllScreen: FavouritesEvent()
    object RefreshBranches: FavouritesEvent()

    object OnChangeAddressClicked : FavouritesEvent()

    object OnDiscoverClicked : FavouritesEvent()

    object BottomSheetAddNewAddressClicked: FavouritesEvent()
    object CloseLocationBottomSheetClicked: FavouritesEvent()

}

sealed class FavouritesState : BaseState {
    data class OpenBranchDetails(val id: Int): FavouritesState()
    object OpenDiscoverScreen: FavouritesState()
    object OpenLocationBottomSheet: FavouritesState()
    object HideLocationBottomSheet : FavouritesState()
    object OpenAddAddressScreen: FavouritesState()


}


data class FavouritesViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var user: MutableState<UserUIModel?> = mutableStateOf(null),
    val selectedLocationId:  MutableState<Int?> = mutableStateOf(null),

    ) : BaseViewState