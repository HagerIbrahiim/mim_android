package com.trianglz.mimar.modules.filter.presenation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.trianglz.core.domain.enum.SimpleDateFormatEnum
import com.trianglz.core.presentation.base.BaseActivity
import com.trianglz.core.presentation.extensions.getActivity
import com.trianglz.core_compose.presentation.composables.BottomSheetContainerLayout
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.domain.extention.getLocalDateFromISO
import com.trianglz.mimar.common.presentation.compose_views.TimePickerDialog
import com.trianglz.mimar.common.presentation.extensions.openDatePicker
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.filter.presenation.composables.FilterScreenContent
import com.trianglz.mimar.modules.filter.presenation.composables.SelectDateBottomSheet
import com.trianglz.mimar.modules.filter.presenation.composables.SelectionBottomSheet
import com.trianglz.mimar.modules.filter.presenation.contract.FilterEvent
import com.trianglz.mimar.modules.filter.presenation.contract.FilterState
import com.trianglz.mimar.modules.filter.presenation.contract.FilterViewState
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.filter.presenation.model.BranchesFilterUIModel
import com.trianglz.mimar.modules.filter.presenation.model.FilterBottomSheetType
import com.trianglz.mimar.modules.filter.presenation.model.FilterNavArgs
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@MainGraph(start = false)
@Destination(navArgsDelegate = FilterNavArgs::class)
@Composable
fun FilterScreen(
    viewModel: FilterViewModel = hiltViewModel(),
    resultNavigator: ResultBackNavigator<BranchesFilterUIModel>,
    navigator: DestinationsNavigator
) {

    //region Status Bar
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )

        onDispose {}
    }
    //endregion

    val context = LocalContext.current

    val viewStates = remember {
        viewModel.viewStates ?: FilterViewState()
    }

    val specialtiesList = remember {
        viewStates.specialtiesList
    }

    val genderList = remember {
        viewStates.servicedGendersList
    }


    val offeredLocationList = remember {
        viewStates.offeredLocationsList
    }

    val formattedPickedTime = remember {
        viewStates.formattedPickedTime
    }

    val selectedAvailabilityTime = remember {
        viewStates.selectedAvailabilityTime
    }


    val pickedDate = remember {
        viewStates.pickedDate
    }

    val pickedTime = remember {
        viewStates.pickedTime
    }

    val formattedPickedDate = remember {
        viewStates.formattedPickedDate
    }

    val sheetData = remember {
        viewStates.sheetData
    }

    val servicedGenderCheckedListCount = remember(genderList?.value) {
        genderList?.value?.filter { it.isChecked.value }?.size
    }

    val specialtiesCheckedListCount = remember(specialtiesList?.value) {
        specialtiesList?.value?.filter { it.isChecked.value }?.size
    }

    val offeredLocationCheckedListCount = remember(offeredLocationList?.value) {
        offeredLocationList?.value?.filter { it.isChecked.value }?.size
    }

    val availabilityTimeCount: @Composable () -> Int = remember(selectedAvailabilityTime.value) {
        { if (selectedAvailabilityTime.value?.first == null ) 0 else 1 }
    }

    val specialitiesText = remember(specialtiesList?.value) {
        specialtiesList?.value?.filter { it.isChecked.value }.takeUnless { it?.isEmpty() == true }
            ?.joinToString(",") { it.title.getString(context) } ?:""
    }

    val servicedGenderText = remember(genderList?.value) {
        genderList?.value?.filter { it.isChecked.value }.takeUnless { it?.isEmpty() == true }
            ?.joinToString(",") { it.title.getString(context) } ?: ""
    }

    val offeredLocationText = remember(offeredLocationList?.value) {
        offeredLocationList?.value?.filter { it.isChecked.value }.takeUnless { it?.isEmpty() == true }
            ?.joinToString(",") { it.title.getString(context) } ?: ""
    }

    val ratingText = remember(viewStates.selectedRating.value) {
        viewStates.selectedRating.value?.title?.getString(context) ?: ""
    }

    val ratingCount: @Composable () -> Int = remember(ratingText) {
        {
            if (ratingText.isEmpty()) 0 else 1
        }
    }


    LaunchedEffect(key1 = pickedDate.value,){
        viewModel.setEvent(FilterEvent.UpdatePickedDate)
    }



    LaunchedEffect(key1 = pickedTime.value){
        viewModel.setEvent(FilterEvent.UpdatePickedTime)
    }


    val backIconClicked = remember {
        {
            viewModel.setEvent(FilterEvent.CloseScreenClicked)

        }
    }

    val specialtiesClicked = remember {
        {
            viewModel.setEvent(FilterEvent.SelectSpecialtiesClicked)
        }
    }

    val selectGenderClicked = remember {
        {
            viewModel.setEvent(FilterEvent.SelectServicedGendersClicked)
        }
    }


    val selectRatingClicked = remember {
        {
            viewModel.setEvent(FilterEvent.SelectRatingClicked)
        }
    }

    val selectAvailabilityTimeClicked = remember {
        {
            viewModel.setEvent(FilterEvent.SelectAvailabilityTimeClicked)
        }
    }

    val selectOfferedLocationsClicked = remember {
        {
            viewModel.setEvent(FilterEvent.SelectOfferedLocationsClicked)
        }
    }

    val resetClicked = remember {
        {
            viewModel.setEvent(FilterEvent.ResetBtnClicked)
        }
    }

    val applyClicked = remember {
        {
            viewModel.setEvent(FilterEvent.ApplyBtnClicked)
        }
    }

    val submitBottomSheetClicked = remember {
        {
            viewModel.setEvent(FilterEvent.BottomSheetSubmitBtnClicked)

        }
    }

    val closeBottomSheetClicked = remember {
        {
            viewModel.setEvent(FilterEvent.CloseBottomSheetClicked)
        }
    }

    val onSelectDateIClicked = remember {
        {
            viewModel.setEvent(FilterEvent.SelectDate)

        }
    }

    val onSelectTimeClicked = remember {
        {
            viewModel.setEvent(FilterEvent.SelectTime)

        }
    }

    val onSubmitDateClicked = remember {
        {
            viewModel.setEvent(FilterEvent.SubmitDateClicked)
        }

    }

    val onSelectAllSwitchStatusChanged: (isChecked: Boolean) -> Unit = remember {
        {
            viewModel.setEvent(FilterEvent.BottomSheetSelectAllSwitchStatusChanged(it))
        }
    }

    val onFilterOptionChecked: (BaseCheckboxItemUiModel) -> Unit = remember {
        {
            viewModel.setEvent(FilterEvent.OnFilterOptionChecked(it))
        }
    }

    val onSelectDateTrailingIconClicked = remember {
        {
            viewModel.setEvent(FilterEvent.SelectDateTrailingIconClicked)
        }
    }

    val onSelectTimeTrailingIconClicked = remember {
        {
            viewModel.setEvent(FilterEvent.SelectTimeTrailingIconClicked)
        }
    }

    val onTimePickerChange: (LocalTime) -> Unit = remember {
        {
            viewModel.viewStates?.pickedTime?.value = it
        }
    }


    val onDatePickerChange: (LocalDate) -> Unit = remember {
        {
            viewModel.viewStates?.pickedDate?.value = it
        }
    }


    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val coroutineScope = rememberCoroutineScope()

    //val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    val currentYear = remember {
        Calendar.getInstance().get(Calendar.YEAR)
    }


    BottomSheetContainerLayout(
        mainScreenContent = {
            FilterScreenContent(
                availabilityTimeCount,
                ratingCount,
                { selectedAvailabilityTime.value },
                { specialtiesCheckedListCount },
                { specialitiesText },
                { offeredLocationCheckedListCount },
                { offeredLocationText },
                { servicedGenderCheckedListCount },
                { servicedGenderText },
                { ratingText },
                backIconClicked,
                specialtiesClicked,
                selectGenderClicked,
                selectRatingClicked,
                selectAvailabilityTimeClicked,
                selectOfferedLocationsClicked,
                resetClicked,
                applyClicked
            )
        },
        bottomSheetContent = {

            if (viewStates.bottomSheetType.value is FilterBottomSheetType.Date) {
                SelectDateBottomSheet(
                    { formattedPickedDate.value },
                    { formattedPickedTime.value },
                    onSelectDateIClicked,
                    onSelectTimeClicked,
                    closeBottomSheetClicked,
                    onSubmitDateClicked,
                    onSelectDateTrailingIconClicked,
                    onSelectTimeTrailingIconClicked,
                )
            }
            else {
                SelectionBottomSheet(
                    selectionItem = { sheetData.value },
                    onSubmitBtnClicked = submitBottomSheetClicked,
                    onSelectAllSwitchStatusChanged = onSelectAllSwitchStatusChanged,
                    onBackButtonClicked = closeBottomSheetClicked,
                    onOptionChecked = onFilterOptionChecked
                )
            }
        },
        sheetState = sheetState
    )


//    DatePickerDialog(
//        dateDialogState = { dateDialogState },
//        initialDate = { pickedDate.value ?: LocalDate.now() },
//        onDateChange = onDatePickerChange,
//        yearRange = currentYear..currentYear.plus(1)
//    )

    TimePickerDialog(
        timeDialogState = { timeDialogState },
        initialTime = { pickedTime.value ?: LocalTime.now() },
        onTimeChange = onTimePickerChange
    )

    GeneralObservers<FilterState, FilterViewModel>(viewModel = viewModel) {
        when (it) {
            FilterState.OpenSelectionBottomSheet -> {
                coroutineScope.launch {
                    viewStates.bottomSheetType.value = FilterBottomSheetType.Selection
                    if (!sheetState.isVisible) sheetState.show()
                }
            }
            FilterState.HideBottomSheet -> {
                coroutineScope.launch {
                    viewStates.bottomSheetType.value = null
                    if (sheetState.isVisible) sheetState.hide()
                }
            }
            FilterState.CloseFilterScreen -> {
                navigator.popBackStack()
            }
            FilterState.OpenDatePicker -> {
                context.getActivity<BaseActivity>()?.openDatePicker(
                    selectedDay = viewStates.formattedPickedDate.value,
                    format = SimpleDateFormatEnum.DayMonthYear4.simpleDateFormat,
                    openCalenderWithCustomDate = true,
                    afterToday = true

                ) { date ->
                    date.getLocalDateFromISO()?.let { onDatePickerChange(it) }
                }
            }
            FilterState.OpenTimePicker -> {
                timeDialogState.show()
            }
            is FilterState.SendFilterDataToPrevious -> {
                resultNavigator.navigateBack(it.data)
            }
            FilterState.ResetFilter -> {
                resultNavigator.setResult(BranchesFilterUIModel())
            }
            FilterState.SubmitBottomSheetData -> {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                }
            }
            FilterState.OpenDateBottomSheet -> {
                coroutineScope.launch {
                    viewStates.bottomSheetType.value = FilterBottomSheetType.Date
                    if (!sheetState.isVisible) sheetState.show()
                }
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }

}