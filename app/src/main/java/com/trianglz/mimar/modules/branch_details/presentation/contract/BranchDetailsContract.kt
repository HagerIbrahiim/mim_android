package com.trianglz.mimar.modules.branch_details.presentation.contract


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.branches.presentation.model.BranchDetailsUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel
import kotlinx.coroutines.flow.MutableStateFlow


/// Events that user performed
sealed class BranchDetailsEvent : BaseEvent {

    object RefreshScreen : BranchDetailsEvent()
    object RefreshServices : BranchDetailsEvent()

    data class SelectSpecialty(val id: Int) : BranchDetailsEvent()
    object FilterClicked : BranchDetailsEvent()

    data class AddServiceToCart(val service: ServiceUIModel) : BranchDetailsEvent()

    data class ToggleBranchFavoriteClicked(val item: BranchUIModel) : BranchDetailsEvent()
    object AllInfoClicked : BranchDetailsEvent()

    object AllReviewsClicked : BranchDetailsEvent()

    object CartClicked : BranchDetailsEvent()
    object BackClicked : BranchDetailsEvent()
    object NotificationClicked : BranchDetailsEvent()
    object CloseFilterClicked: BranchDetailsEvent()
    data class SubmitFilterClicked(val list: List<BaseCheckboxItemUiModel>?): BranchDetailsEvent()

}

sealed class BranchDetailsState : BaseState {

    object OpenFilter : BranchDetailsState()
    object HideFilter : BranchDetailsState()
    object OpenCart : BranchDetailsState()
    object OpenNotificationsList : BranchDetailsState()
    object FinishScreen : BranchDetailsState()
    data class  OpenAllInfo(val branchId: Int) : BranchDetailsState()
    data class OpenAllReviews(val branchId: Int) : BranchDetailsState()


}

data class BranchDetailsViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var specialtiesList: SnapshotStateList<SpecialtiesUIModel>? = mutableStateListOf(),
    val selectedSpecialty: MutableState<SpecialtiesUIModel?> = mutableStateOf(null),
    val branchDetails: MutableState<BranchDetailsUIModel?> = mutableStateOf(null),
    var specialtyId: Int = 0,
    val branchId: MutableState<Int?> = mutableStateOf(null),
    val isBackToHome: MutableState<Boolean> = mutableStateOf(false),
    val filteredOfferedLocation: MutableState<String?> = mutableStateOf(null),
    val updateFilterData: MutableState<Boolean?> = mutableStateOf(true),


    ) : BaseViewState
