package com.trianglz.mimar.modules.offered_location.presentation.composables

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.filter.presenation.FilterViewModel
import com.trianglz.mimar.modules.filter.presenation.composables.SelectionBottomSheet
import com.trianglz.mimar.modules.filter.presenation.contract.FilterEvent
import com.trianglz.mimar.modules.filter.presenation.contract.FilterState
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.filter.presenation.model.BaseSelectionUiModel
import com.trianglz.mimar.modules.offered_location.presentation.model.OfferedLocationsUIModel

@Composable
fun OfferedLocationBottomSheet(filterViewModel: FilterViewModel =  hiltViewModel(),
                               updateFilterData: MutableState<Boolean?>,
                               closeFilter: () -> Unit,
                               submitFilter: (list: List<BaseCheckboxItemUiModel>?) -> Unit) {

    LaunchedEffect(updateFilterData.value){
        if(updateFilterData.value == true) {
            filterViewModel.viewStates?.sheetData?.value =  BaseSelectionUiModel(
                title = StringWrapper(R.string.offered_locations),
                items = OfferedLocationsUIModel.getShimmerList().toMutableStateList(),
                selectAll = mutableStateOf(false)
            )
            filterViewModel.setEvent(FilterEvent.SelectOfferedLocationsClicked)
            updateFilterData.value = false
        }
    }

    val closeBottomSheet = remember {
        {
            filterViewModel.setEvent(FilterEvent.CloseBottomSheetClicked)
        }
    }

    val onSelectAllSwitchStatusChanged : (Boolean) -> Unit = remember {
        {
            filterViewModel.setEvent(FilterEvent.BottomSheetSelectAllSwitchStatusChanged(it))
        }
    }

    val onOptionClicked: (BaseCheckboxItemUiModel) -> Unit = remember {
        {
            filterViewModel.setEvent(FilterEvent.OnFilterOptionChecked(it))
        }
    }

    val onSubmitClicked = remember {
        {
            filterViewModel.setEvent(FilterEvent.BottomSheetSubmitBtnClicked)
        }
    }

    SelectionBottomSheet(
        selectionItem = { filterViewModel.viewStates?.sheetData?.value },
        onSubmitBtnClicked = onSubmitClicked,
        onSelectAllSwitchStatusChanged = onSelectAllSwitchStatusChanged,
        onBackButtonClicked = closeBottomSheet,
        onOptionChecked = onOptionClicked
    )

    GeneralObservers<FilterState, FilterViewModel>(viewModel = filterViewModel) {
        when (it) {
            FilterState.HideBottomSheet -> {
                closeFilter()
            }

            FilterState.SubmitBottomSheetData ->{
                submitFilter(filterViewModel.viewStates?.sheetData?.value?.items)
                closeFilter()
            }
            else -> {}
        }

    }

}