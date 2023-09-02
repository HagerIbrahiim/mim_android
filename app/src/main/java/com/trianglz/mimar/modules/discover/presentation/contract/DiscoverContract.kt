package com.trianglz.mimar.modules.discover.presentation.contract

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel
import com.trianglz.mimar.modules.filter.presenation.model.BranchesFilterUIModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import kotlinx.coroutines.flow.MutableStateFlow

// Events that user performed
sealed class DiscoverEvent : BaseEvent {
    object RefreshScreen: DiscoverEvent()
    object RefreshBranches: DiscoverEvent()
    object OnSearchClicked: DiscoverEvent()
    object LocationPermissionGrantedSuccessfully : DiscoverEvent()
    object OnRequestLocationClicked : DiscoverEvent()
    object OnChangeAddressClicked : DiscoverEvent()
    object OnFilterClicked : DiscoverEvent()

    object BottomSheetAddNewAddressClicked: DiscoverEvent()
    object CloseBLocationBottomSheetClicked: DiscoverEvent()

    data class SelectCategory(val id: Int): DiscoverEvent()
    data class ItemBranchClicked(val id: Int): DiscoverEvent()
    data class ToggleBranchFavoriteClicked(val item: BranchUIModel): DiscoverEvent()
    data class ApplyFilter(val filter: BranchesFilterUIModel): DiscoverEvent()

}

sealed class DiscoverState: BaseState {
    object OpenSignupScreen: DiscoverState()
    data class OpenSearchScreen(val location: Location): DiscoverState()
    object AskForLocationPermission : DiscoverState()
    object OpenLocationBottomSheet: DiscoverState()
    object HideLocationBottomSheet : DiscoverState()
    object OpenAddAddressScreen: DiscoverState()
    data class OpenFilterDialog(val filter: BranchesFilterUIModel?): DiscoverState()
    data class OpenCategoryDetails(val id: Int): DiscoverState()
    data class OpenBranchDetails(val id: Int): DiscoverState()
}

data class DiscoverViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var user: MutableState<UserUIModel?> = mutableStateOf(null),
    var categories: SnapshotStateList<CategoryUIModel>? = mutableStateListOf(),
    val selectedCategory: MutableState<CategoryUIModel?> = mutableStateOf(null),
    var selectedCategoryId: Int = 0,
    val location:  MutableState<Location?> = mutableStateOf(null),
    val selectedLocationId:  MutableState<Int?> = mutableStateOf(null),
    val branchesFilter: MutableState<BranchesFilterUIModel?> = mutableStateOf(null),

    ) : BaseViewState {
    val hasFilterData: MutableState<Boolean>
        get() {
            val filterData = branchesFilter.value
            return mutableStateOf(
                !filterData?.genderList.isNullOrEmpty() || filterData?.pickedDate != null || filterData?.pickedTime != null
                        || !filterData?.specialties.isNullOrEmpty() || !filterData?.offeredLocations.isNullOrEmpty()
                        || filterData?.selectedRating != null
            )
        }
}