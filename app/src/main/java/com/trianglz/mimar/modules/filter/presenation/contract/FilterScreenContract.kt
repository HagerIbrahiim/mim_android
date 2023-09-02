package com.trianglz.mimar.modules.filter.presenation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.filter.presenation.model.BaseSelectionUiModel
import com.trianglz.mimar.modules.filter.presenation.model.BranchesFilterUIModel
import com.trianglz.mimar.modules.filter.presenation.model.FilterBottomSheetType
import com.trianglz.mimar.modules.offered_location.presentation.model.OfferedLocationsUIModel
import com.trianglz.mimar.modules.ratings.presenation.model.RatingUIModel
import com.trianglz.mimar.modules.serviced_genders.presenation.model.ServicedGenderUIModel
import com.trianglz.mimar.modules.setup_profile.presentation.contract.SetupProfileEvent
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime

sealed class FilterEvent : BaseEvent {
    object ApplyBtnClicked : FilterEvent()
    object ResetBtnClicked : FilterEvent()
    object SelectSpecialtiesClicked : FilterEvent()
    object SelectServicedGendersClicked : FilterEvent()
    object SelectOfferedLocationsClicked : FilterEvent()
    object SelectAvailabilityTimeClicked : FilterEvent()
    object SelectRatingClicked : FilterEvent()
    object CloseScreenClicked: FilterEvent()
    object BottomSheetSubmitBtnClicked : FilterEvent()
    object CloseBottomSheetClicked: FilterEvent()
    object SelectDate : FilterEvent()
    object SelectTime : FilterEvent()
    object UpdatePickedDate : FilterEvent()
    object UpdatePickedTime : FilterEvent()
    object SubmitDateClicked : FilterEvent()
    object SelectDateTrailingIconClicked : FilterEvent()
    object SelectTimeTrailingIconClicked : FilterEvent()

    data class OnFilterOptionChecked(val filterOption: BaseCheckboxItemUiModel): FilterEvent()
    data class BottomSheetSelectAllSwitchStatusChanged(val isChecked: Boolean) : FilterEvent()

}

sealed class FilterState : BaseState {
    object OpenSelectionBottomSheet : FilterState()
    object OpenDateBottomSheet : FilterState()

    object HideBottomSheet : FilterState()
    object CloseFilterScreen : FilterState()
    object OpenDatePicker : FilterState()
    object OpenTimePicker : FilterState()
    object ResetFilter: FilterState()
    object SubmitBottomSheetData : FilterState()

    data class SendFilterDataToPrevious(val data: BranchesFilterUIModel): FilterState()


}

data class FilterViewState constructor(

    var specialtiesList: MutableState<List<SpecialtiesUIModel>>? = mutableStateOf(listOf()),
    var servicedGendersList: MutableState<List<ServicedGenderUIModel>>? = mutableStateOf(listOf()),
    var offeredLocationsList: MutableState<List<OfferedLocationsUIModel>>? = mutableStateOf(listOf()),
    var selectedRating: MutableState<RatingUIModel?> = mutableStateOf(null),
    val sheetData: MutableState<BaseSelectionUiModel?> = mutableStateOf(null),
    val pickedDate: MutableState<LocalDate?> = mutableStateOf(null),
    val pickedTime: MutableState<LocalTime?> = mutableStateOf(null),
    val formattedPickedDate: MutableState<String?> = mutableStateOf(null),
    val formattedPickedTime: MutableState<String?> = mutableStateOf(null),
    val selectedAvailabilityTime: MutableState<Pair<LocalDate?,LocalTime?>?> = mutableStateOf(null),
    val branchesFilter: MutableState<BranchesFilterUIModel?> = mutableStateOf(null),
    val bottomSheetType: MutableState<FilterBottomSheetType?> = mutableStateOf(null),
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    ) : BaseViewState {

}