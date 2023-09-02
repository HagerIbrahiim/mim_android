package com.trianglz.mimar.modules.user_home.presentation.contract

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import com.trianglz.mimar.modules.user_home.presentation.model.*
import kotlinx.coroutines.flow.MutableStateFlow

// Events that user performed
sealed class UserHomeEvent : BaseEvent {
    object RefreshScreen: UserHomeEvent()
    object OnGetStartedClicked: UserHomeEvent()
    object OnSearchClicked: UserHomeEvent()
    object CategoriesSeeMoreClicked: UserHomeEvent()
    object FavoritesSeeMoreClicked: UserHomeEvent()
    object PopularProvidersSeeMoreClicked: UserHomeEvent()
    object LocationPermissionGrantedSuccessfully : UserHomeEvent()
    object OnRequestLocationClicked : UserHomeEvent()
    object OnChangeAddressClicked : UserHomeEvent()
    object BottomSheetAddNewAddressClicked: UserHomeEvent()
    object CloseLocationBottomSheetClicked: UserHomeEvent()

    data class OnLastAppointmentClicked(val id: Int): UserHomeEvent()
    data class ItemCategoryClicked(val id: Int): UserHomeEvent()
    data class ItemBranchClicked(val id: Int): UserHomeEvent()
    data class ToggleBranchFavoriteClicked(val item: BranchUIModel): UserHomeEvent()

}

sealed class UserHomeState: BaseState {
    object OpenSignupScreen: UserHomeState()
    object OpenAllCategories: UserHomeState()
    object OpenAllFavorites: UserHomeState()
    object OpenAllPopularProviders: UserHomeState()
    data class OpenSearchScreen(val location: Location): UserHomeState()
    object AskForLocationPermission : UserHomeState()
    object OpenLocationBottomSheet: UserHomeState()
    object HideLocationBottomSheet : UserHomeState()
    object OpenAddAddressScreen: UserHomeState()

    data class OpenAppointmentDetails(val id: Int): UserHomeState()
    data class OpenCategoryDetails(val id: Int): UserHomeState()
    data class OpenBranchDetails(val id: Int): UserHomeState()

}

data class UserHomeViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
//    var searchText: MutableState<String> = mutableStateOf(""),
    var user: MutableState<UserUIModel?> = mutableStateOf(null),
    var isStartAnimation: MutableState<Boolean> = mutableStateOf(false),
    var categories: MutableState<CategoriesSectionUIModel?> = mutableStateOf(null),
    var favoriteBranches: MutableState<BranchesSectionUIModel?> = mutableStateOf(null),
    var popularBranches: MutableState<BranchesSectionUIModel?> = mutableStateOf(null),
    var guestWelcomeMessage: MutableState<GuestWelcomeSectionUIModel> = mutableStateOf(GuestWelcomeSectionUIModel(false)),
    var lastAppointment: MutableState<LastAppointmentSectionUIModel?> = mutableStateOf(null),
    var ourCategoriesError: MutableState<Boolean> = mutableStateOf(false),
    var lastAppointmentError: MutableState<Boolean> = mutableStateOf(false),
    var favoriteBranchesError: MutableState<Boolean> = mutableStateOf(false),
    var popularBranchesError: MutableState<Boolean> = mutableStateOf(false),
    val location:  MutableState<Location?> = mutableStateOf(null),
    val selectedLocationId:  MutableState<Int?> = mutableStateOf(null),
    ): BaseViewState {
    val list: SnapshotStateList<MutableState<out BaseUserHomeUIModel?>>
        = mutableStateListOf(guestWelcomeMessage, lastAppointment, categories, favoriteBranches, popularBranches)

    override val networkError: MutableStateFlow<Boolean>
        get() {
            return MutableStateFlow(
                if (user.value == null)
                    ourCategoriesError.value  && popularBranchesError.value
                else
                    ourCategoriesError.value && favoriteBranchesError.value && popularBranchesError.value

            )
        }
}