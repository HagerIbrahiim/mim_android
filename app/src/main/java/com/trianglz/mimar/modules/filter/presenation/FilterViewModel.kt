package com.trianglz.mimar.modules.filter.presenation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.mimar.R
import com.trianglz.mimar.common.domain.extention.toFormattedHourMinutes12Format
import com.trianglz.mimar.common.domain.extention.toHourMinutes24Format
import com.trianglz.mimar.modules.destinations.FilterScreenDestination
import com.trianglz.mimar.modules.filter.presenation.contract.FilterEvent
import com.trianglz.mimar.modules.filter.presenation.contract.FilterState
import com.trianglz.mimar.modules.filter.presenation.contract.FilterViewState
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.filter.presenation.model.BaseSelectionUiModel
import com.trianglz.mimar.modules.filter.presenation.model.BranchesFilterUIModel
import com.trianglz.mimar.modules.filter.presenation.model.SelectionType
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.offered_location.domain.model.getOfferedLocationFilterKey
import com.trianglz.mimar.modules.offered_location.domain.usecase.GetOfferedLocationsUseCase
import com.trianglz.mimar.modules.offered_location.domain.usecase.ResetOfferedLocationsDataUseCase
import com.trianglz.mimar.modules.offered_location.domain.usecase.SubmitOfferedLocationsUseCase
import com.trianglz.mimar.modules.offered_location.presentation.mapper.toDomain
import com.trianglz.mimar.modules.offered_location.presentation.mapper.toUI
import com.trianglz.mimar.modules.offered_location.presentation.model.OfferedLocationsUIModel
import com.trianglz.mimar.modules.ratings.domain.usecase.GetFilterRatingsListUseCase
import com.trianglz.mimar.modules.ratings.domain.usecase.ResetFilterRatingsUseCase
import com.trianglz.mimar.modules.ratings.domain.usecase.SubmitFilterRatingUseCase
import com.trianglz.mimar.modules.ratings.presenation.mapper.toDomain
import com.trianglz.mimar.modules.ratings.presenation.mapper.toUI
import com.trianglz.mimar.modules.ratings.presenation.model.RatingUIModel
import com.trianglz.mimar.modules.serviced_genders.domain.model.ServicedGenderType
import com.trianglz.mimar.modules.serviced_genders.domain.model.getServicedGenderFilterKey
import com.trianglz.mimar.modules.serviced_genders.domain.usecase.GetServicedGenderUseCase
import com.trianglz.mimar.modules.serviced_genders.domain.usecase.ResetServicedGendersDataUseCase
import com.trianglz.mimar.modules.serviced_genders.domain.usecase.SubmitServicedGendersUseCase
import com.trianglz.mimar.modules.serviced_genders.presenation.mapper.toDomain
import com.trianglz.mimar.modules.serviced_genders.presenation.mapper.toUI
import com.trianglz.mimar.modules.serviced_genders.presenation.model.ServicedGenderUIModel
import com.trianglz.mimar.modules.specilaities.domain.usecase.ClearSpecialitiesUseCase
import com.trianglz.mimar.modules.specilaities.domain.usecase.GetSpecialtiesUseCase
import com.trianglz.mimar.modules.specilaities.domain.usecase.SubmitSpecialtiesUseCase
import com.trianglz.mimar.modules.specilaities.presenation.mapper.toDomain
import com.trianglz.mimar.modules.specilaities.presenation.mapper.toUI
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val getSpecialtiesUseCase: GetSpecialtiesUseCase,
    private val getOfferedLocationsUseCase: GetOfferedLocationsUseCase,
    private val getServicedGenderUseCase: GetServicedGenderUseCase,
    private val submitSpecialtiesUseCase: SubmitSpecialtiesUseCase,
    private val submitServicedGendersUseCase: SubmitServicedGendersUseCase,
    private val submitOfferedLocationsUseCase: SubmitOfferedLocationsUseCase,
    private val getRatingsUseCase: GetFilterRatingsListUseCase,
    private val submitRatingUseCase: SubmitFilterRatingUseCase,
    private val resetRatingsUseCase: ResetFilterRatingsUseCase,
    private val resetOfferedLocationsDataUseCase: ResetOfferedLocationsDataUseCase,
    private val resetServicedGendersDataUseCase: ResetServicedGendersDataUseCase,
    private val clearSpecialtiesUseCase: ClearSpecialitiesUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application
    ) : BaseMVIViewModel<FilterEvent, FilterViewState, FilterState>(
) {

    private val pickedDate get() = viewStates?.pickedDate?.value
    private val pickedTime get() = viewStates?.pickedTime?.value
    private val filterData get() = viewStates?.branchesFilter?.value
    private val formattedPickedDate get() = viewStates?.formattedPickedDate?.value
    private val formattedPickedTime get() = viewStates?.formattedPickedTime?.value
    private val selectedAvailabilityTime get() = viewStates?.selectedAvailabilityTime?.value

    init {
        saveDataFromNavArg()
        handlePreSelectedData()
    }

    override fun handleEvents(event: FilterEvent) {

        when(event){
            FilterEvent.ApplyBtnClicked -> {
                onApplyClicked()
            }
            FilterEvent.CloseScreenClicked ->{
                setState { FilterState.CloseFilterScreen }
            }
            FilterEvent.ResetBtnClicked -> {
                resetViewStates()
            }
            FilterEvent.SelectAvailabilityTimeClicked -> {
                openDateDialog()
            }
            FilterEvent.SelectOfferedLocationsClicked -> {
                getOfferedLocations()
            }
            FilterEvent.SelectRatingClicked -> {
                getRatings()
            }
            FilterEvent.SelectServicedGendersClicked -> {
                getServicedGenders()
            }
            FilterEvent.SelectSpecialtiesClicked -> {
                getSpecialties()
            }
            is FilterEvent.OnFilterOptionChecked-> {
                handleFilterOptionClickAction(event.filterOption)
            }
            is FilterEvent.BottomSheetSelectAllSwitchStatusChanged -> {
                handleBottomSheetSelectAll(event.isChecked)
            }
            FilterEvent.BottomSheetSubmitBtnClicked -> {
                handleBottomSheetSubmitClick()
            }
            FilterEvent.CloseBottomSheetClicked -> {
                setState { FilterState.HideBottomSheet }
            }
            FilterEvent.SelectDate -> {
                setState { FilterState.OpenDatePicker }
            }

            FilterEvent.SelectTime -> {
                setState { FilterState.OpenTimePicker }
            }
            FilterEvent.UpdatePickedDate -> {
                setFormattedPickedDate()
            }
            FilterEvent.UpdatePickedTime -> {
                setFormattedPickedTime()
            }
            FilterEvent.SubmitDateClicked ->{
                updateSelectedAvailabilityTime()
                setState { FilterState.HideBottomSheet }
            }

            FilterEvent.SelectDateTrailingIconClicked -> {
                handleDateSelection()
            }
            FilterEvent.SelectTimeTrailingIconClicked -> {
                handleTimeSelection()
            }
        }
    }

    private fun saveDataFromNavArg(){
        val filter = FilterScreenDestination.argsFrom(savedStateHandle).branchesFilter
        viewStates?.branchesFilter?.value = filter
    }

    private fun handleFilterOptionClickAction(filterOption: BaseCheckboxItemUiModel) {
        val sheetData = viewStates?.sheetData?.value?.items

        when(filterOption){
            is RatingUIModel -> {
                val clickedItem = sheetData?.find { it.id == filterOption.id }
                if(clickedItem?.isChecked?.value == false) {
                    sheetData.firstOrNull { it.isChecked.value }?.isChecked?.value = false
                }
                clickedItem?.isChecked?.value = !(clickedItem?.isChecked?.value ?: false)
            }
            else -> {
                sheetData?.find { it.id == filterOption.id }?.isChecked?.value = !filterOption.isChecked.value
                viewStates?.sheetData?.value?.selectAll?.value =  sheetData?.all { it.isChecked.value } ?: false
            }
        }
    }

    private fun openDateDialog(){
        if (viewStates?.selectedAvailabilityTime?.value == null) {
            viewStates?.pickedTime?.value = null
            viewStates?.pickedDate?.value = null
        } else {
            viewStates?.pickedDate?.value = viewStates?.selectedAvailabilityTime?.value?.first
            setFormattedPickedDate()
            viewStates?.pickedTime?.value = viewStates?.selectedAvailabilityTime?.value?.second
            setFormattedPickedTime()
        }
        viewStates?.sheetData?.value = BaseSelectionUiModel(
            title = StringWrapper(R.string.select_time),
            items = null,
            selectAll = mutableStateOf(true)
        )

        setState { FilterState.OpenDateBottomSheet }
    }
    private fun handleBottomSheetSelectAll(isChecked: Boolean){
        val sheetData = viewStates?.sheetData?.value

        val newList = SnapshotStateList<BaseCheckboxItemUiModel>()

        sheetData?.items?.forEach { item ->
            newList.add(item.apply {
                this.isChecked.value = isChecked
            })
        }
        viewStates?.sheetData?.value?.selectAll?.value = isChecked
    }

    /**
     * Used submit use cases to keep saved list in repository updated with new submitted values
     */
    private fun handleBottomSheetSubmitClick(){
        val items = viewStates?.sheetData?.value?.items
        if(items?.isNotEmpty() == true){
            when(items[0]){
                is SpecialtiesUIModel ->{
                    viewStates?.specialtiesList?.value = items.toList() as List<SpecialtiesUIModel>
                    submitSpecialtiesUseCase.execute((items as? SnapshotStateList<SpecialtiesUIModel>)?.map { it.toDomain(application.baseContext) } ?: listOf())
                }

                is ServicedGenderUIModel ->{
                    viewStates?.servicedGendersList?.value = items.toList() as List<ServicedGenderUIModel>
                    submitServicedGendersUseCase.execute((items as? SnapshotStateList<ServicedGenderUIModel>)?.map { it.toDomain(application.baseContext) } ?: listOf())
                }

                is OfferedLocationsUIModel ->{
                    viewStates?.offeredLocationsList?.value = items.toList() as List<OfferedLocationsUIModel>
                    submitOfferedLocationsUseCase.execute((items as? SnapshotStateList<OfferedLocationsUIModel>)?.map { it.toDomain(application.baseContext) } ?: listOf())

                }

                is RatingUIModel ->{
                    viewStates?.selectedRating?.value = (items.toList() as List<RatingUIModel> ). firstOrNull { it.isChecked.value }
                    submitRatingUseCase.execute((items as? SnapshotStateList<RatingUIModel>)?.map { it.toDomain() } ?: listOf())
                }
            }
        }

        setState { FilterState.SubmitBottomSheetData }
    }


    private fun getSpecialties(){

        launchCoroutine {
            setLoading()

            val fetchedSpecialties = getSpecialtiesUseCase.execute()
            val specialtiesSnapShot = SnapshotStateList<SpecialtiesUIModel>()

            // Handle changing item isChecked status temporary inside bottom sheet
            // Will change it permanently when user clicks 'Submit

            fetchedSpecialties.map { fetchedSpecialty ->
                fetchedSpecialty.toUI()
            }.toCollection(specialtiesSnapShot)

            viewStates?.specialtiesList?.value = (specialtiesSnapShot)

            viewStates?.sheetData?.value = BaseSelectionUiModel(
                title = StringWrapper(R.string.specialties),
                items = specialtiesSnapShot,
                selectAll = mutableStateOf(specialtiesSnapShot.all { it.isChecked.value })
            )

            setDoneLoading()
            setState { FilterState.OpenSelectionBottomSheet }
        }
    }


    private fun getOfferedLocations(){
        launchCoroutine {
            setLoading()

            val fetchedOfferedLocations = getOfferedLocationsUseCase.execute()
            val offeredLocationsSnapShot = SnapshotStateList<OfferedLocationsUIModel>()

            // Handle changing item isChecked status temporary inside bottom sheet
            // Will change it permanently when user clicks 'Submit

            fetchedOfferedLocations.map { fetchedOfferedLocation ->
                fetchedOfferedLocation.toUI()
            }.toCollection(offeredLocationsSnapShot)

            viewStates?.offeredLocationsList?.value =(offeredLocationsSnapShot)

            viewStates?.sheetData?.value = BaseSelectionUiModel(
                title = StringWrapper(R.string.offered_locations),
                items = offeredLocationsSnapShot,
                selectAll = mutableStateOf(offeredLocationsSnapShot.all { it.isChecked.value })
            )

            setDoneLoading()
            setState { FilterState.OpenSelectionBottomSheet }
        }
    }


    private fun getServicedGenders(){
        launchCoroutine {
            setLoading()

            val fetchedServicedGender = getServicedGenderUseCase.execute()
            val servicedGenderSnapShot = SnapshotStateList<ServicedGenderUIModel>()

            // Handle changing item isChecked status temporary inside bottom sheet
            // Will change it permanently when user clicks 'Submit

            fetchedServicedGender.map { fetchedServicedGender ->
                fetchedServicedGender.toUI()
            }.toCollection(servicedGenderSnapShot)

            viewStates?.servicedGendersList?.value =(servicedGenderSnapShot)

            viewStates?.sheetData?.value = BaseSelectionUiModel(
                title = StringWrapper(R.string.serviced_genders),
                items = servicedGenderSnapShot,
                selectAll = mutableStateOf(servicedGenderSnapShot.all { it.isChecked.value })
            )

            setDoneLoading()
            setState { FilterState.OpenSelectionBottomSheet }
        }
    }

    private fun getRatings(){
        launchCoroutine {
            setLoading()

            val fetchedRatings = getRatingsUseCase.execute()
            val ratingsSnapShot = SnapshotStateList<RatingUIModel>()

            // Handle changing item isChecked status temporary inside bottom sheet
            // Will change it permanently when user clicks 'Submit

            fetchedRatings.map { fetchedRating ->
                fetchedRating.toUI()
            }.toCollection(ratingsSnapShot)

            //viewStates?.ratingList?.value =(specialtiesSnapShot)

            viewStates?.sheetData?.value = BaseSelectionUiModel(
                title = StringWrapper(R.string.rating),
                items = ratingsSnapShot,
                selectAll = mutableStateOf(ratingsSnapShot.all { it.isChecked.value }),
                selectionType = SelectionType.SingleSelection)

            setDoneLoading()
            setState { FilterState.OpenSelectionBottomSheet }
        }
    }

    private fun onApplyClicked(){

        val specialties = viewStates?.specialtiesList?.value?.filter { it.isChecked.value}
        val selectedOfferedLocationsList = viewStates?.offeredLocationsList?.value?.filter { it.isChecked.value}
        val selectedRating = viewStates?.selectedRating?.value?.toDomain()?.rating
        val selectedGender = viewStates?.servicedGendersList?.value?.filter { it.isChecked.value}
        val pickedTime = selectedAvailabilityTime?.second
        val pickedDate = selectedAvailabilityTime?.first

        setState {
            FilterState.SendFilterDataToPrevious (BranchesFilterUIModel(
                if(!specialties.isNullOrEmpty()) specialties else null,
                selectedOfferedLocationsList,
                selectedRating, selectedGender, pickedTime, pickedDate
            ))
        }

    }
    override fun resetViewStates() {
        super.resetViewStates()

        launchCoroutine {
            resetOfferedLocationsDataUseCase.execute()
            clearSpecialtiesUseCase.execute()
            resetServicedGendersDataUseCase.execute()
            resetRatingsUseCase.execute()
            viewStates?.specialtiesList?.value = listOf()
            viewStates?.offeredLocationsList?.value = listOf()
            viewStates?.servicedGendersList?.value = listOf()
            viewStates?.selectedRating?.value =  null
            viewStates?.pickedDate?.value = null
            viewStates?.pickedTime?.value  = null
            viewStates?.selectedAvailabilityTime?.value = null
        }
        setState { FilterState.ResetFilter }
    }

    private fun handlePreSelectedData() {
        launchCoroutine {
            handlePreSelectedDateAndTime()
            handlePreSelectedGender()
            handlePreSelectedSpecialities()
            handlePreSelectedRating()
            handlePreSelectedOfferedLocation()
        }
    }

    private suspend fun handlePreSelectedGender(){
        filterData?.genderList?.let { genders ->
            val preSelectedGenders = genders.map { it.toDomain(application.baseContext) }.getServicedGenderFilterKey()
            val gendersList = getServicedGenderUseCase.execute().map { it.toUI() }
            if (preSelectedGenders != ServicedGenderType.Both.serverName)
                gendersList.find { it.value == preSelectedGenders }?.isChecked?.value = true
            else
                gendersList.onEach { it.isChecked.value = true }

            viewStates?.servicedGendersList?.value  = gendersList

            submitServicedGendersUseCase.execute(gendersList.map { it.toDomain(application.baseContext) })
        }
    }

    private suspend fun handlePreSelectedSpecialities(){
        filterData?.specialties?.let {
            val selectedSpecialities = it.map { it.id }

            val specialties = getSpecialtiesUseCase.execute().map { it.toUI() }
            specialties.filter { it.id in selectedSpecialities }.onEach {
                it.isChecked.value = true
            }

            viewStates?.specialtiesList?.value  = specialties
            submitSpecialtiesUseCase.execute(specialties.map { it.toDomain(application.baseContext) })

        }
    }

    private suspend fun handlePreSelectedOfferedLocation(){
        filterData?.offeredLocations?.let { offeredLocation->
            val preSelectedOfferedLocations = offeredLocation.map { it.toDomain(application.baseContext) }.getOfferedLocationFilterKey()

            val offeredLocationList = getOfferedLocationsUseCase.execute().map { it.toUI() }
            if (preSelectedOfferedLocations != OfferedLocationType.Both(StringWrapper("")).key)
                offeredLocationList.find { it.value == preSelectedOfferedLocations }?.isChecked?.value = true
            else
                offeredLocationList.onEach { it.isChecked.value = true }

            viewStates?.offeredLocationsList?.value  = offeredLocationList

            submitOfferedLocationsUseCase.execute(offeredLocationList.map { it.toDomain(application.baseContext) })
        }
    }

    private  fun handlePreSelectedRating(){
        filterData?.selectedRating?.let { rating ->
            val ratingsList = getRatingsUseCase.execute().map { it.toUI() }
            ratingsList.find { it.isChecked.value }?.isChecked?.value = false
            val preSelectedRating = ratingsList.find { it.rating == rating }
            preSelectedRating?.isChecked?.value = true
            viewStates?.selectedRating?.value  = preSelectedRating
            submitRatingUseCase.execute(ratingsList.map { it.toDomain() })
        }
    }

    private fun handlePreSelectedDateAndTime(){
        filterData?.pickedDate?.let {
            viewStates?.pickedDate?.value = it
            setFormattedPickedDate()
        }

        filterData?.pickedTime?.let {
            viewStates?.pickedTime?.value = it
            setFormattedPickedTime()
            updateSelectedAvailabilityTime()
        }
        viewStates?.selectedAvailabilityTime?.value = Pair(filterData?.pickedDate,filterData?.pickedTime)
    }
    private fun setFormattedPickedTime(){
        viewStates?.formattedPickedTime?.value =if(pickedTime != null) pickedTime?.toFormattedHourMinutes12Format() else null
    }

    private fun setFormattedPickedDate(){
        viewStates?.formattedPickedDate?.value =if(pickedDate != null) "${pickedDate?.year}-${pickedDate?.monthValue}-${pickedDate?.dayOfMonth}"
        else null
    }

    private fun updateSelectedAvailabilityTime(){
        viewStates?.selectedAvailabilityTime?.value = Pair(pickedDate ,pickedTime)
    }

    private fun handleDateSelection(){
        if(formattedPickedDate.isNullOrEmpty())
            setState { FilterState.OpenDatePicker }
        else {
             viewStates?.pickedDate?.value = null
        }
    }

    private fun handleTimeSelection(){
        if(formattedPickedTime.isNullOrEmpty())
            setState { FilterState.OpenTimePicker }
        else {
            viewStates?.pickedTime?.value = null
        }
    }

    override fun createInitialViewState(): FilterViewState {
        return FilterViewState()
    }
}