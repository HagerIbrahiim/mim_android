package com.trianglz.mimar.modules.search.presentation.contract


import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.common.presentation.tabs.models.TabItemUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import kotlinx.coroutines.flow.MutableStateFlow


/// Events that user performed
sealed class SearchEvent : BaseEvent {
    data class TabChanged(val index: Int) : SearchEvent()
    data class ItemBranchClicked(val id: Int): SearchEvent()
    data class ToggleBranchFavoriteClicked(val item: BranchUIModel): SearchEvent()
    data class ServiceItemClicked(val service: ServiceUIModel) : SearchEvent()
    data class AddServiceToCart(val item: ServiceUIModel): SearchEvent()
    object BackIconClicked: SearchEvent()

}

sealed class SearchState : BaseState {
    data class OpenBranchDetails(val branchId: Int,val specialityId: Int?= null): SearchState()
    object FinishScreen: SearchState()

}

data class SearchViewState(
    val searchText:  MutableState<String?> = mutableStateOf(null),
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val showProvidersTab: MutableState<Boolean> = mutableStateOf(true),
    val scrollToFirstPosition : MutableState<Boolean> = mutableStateOf(false),
    var user: MutableState<UserUIModel?> = mutableStateOf(null),
    val location:  MutableState<Location?> = mutableStateOf(null),
    val tabList:  SnapshotStateList<TabItemUIModel> = mutableStateListOf(),


    ) : BaseViewState