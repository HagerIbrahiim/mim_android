package com.trianglz.mimar.modules.branch_details.presentation

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.mimar.common.presentation.base.GeneralUpdatesViewModel
import com.trianglz.mimar.modules.branch_details.presentation.contract.BranchDetailsEvent
import com.trianglz.mimar.modules.branch_details.presentation.contract.BranchDetailsState
import com.trianglz.mimar.modules.branch_details.presentation.contract.BranchDetailsViewState
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchDetailsUseCase
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchFavouritesUpdates
import com.trianglz.mimar.modules.branches.presentation.mapper.toDomainModel
import com.trianglz.mimar.common.presentation.mapper.toUI
import com.trianglz.mimar.modules.branches.presentation.mapper.toUI
import com.trianglz.mimar.modules.destinations.BranchDetailsScreenDestination
import com.trianglz.mimar.modules.offered_location.domain.model.getOfferedLocationFilterKey
import com.trianglz.mimar.modules.offered_location.presentation.mapper.toDomain
import com.trianglz.mimar.modules.offered_location.presentation.model.OfferedLocationsUIModel
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.services.domain.usecase.GetServicesUpdates
import com.trianglz.mimar.modules.services.presentation.mapper.toDomain
import com.trianglz.mimar.modules.services.presentation.mapper.toUI
import com.trianglz.mimar.modules.services.presentation.source.ServicesListSource
import com.trianglz.mimar.modules.specilaities.domain.usecase.GetBranchSpecialitiesUseCase
import com.trianglz.mimar.modules.specilaities.presenation.mapper.toUI
import com.trianglz.mimar.modules.specilaities.presenation.model.SpecialtiesUIModel
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BranchDetailsViewModel @Inject constructor(
    getBranchFavouritesUpdates: GetBranchFavouritesUpdates,
    getServicesUpdates: GetServicesUpdates,
    private val getSpecialtiesUseCase: GetBranchSpecialitiesUseCase,
    private val getBranchDetailsUseCase: GetBranchDetailsUseCase,
    val source: ServicesListSource,
    private val savedStateHandle: SavedStateHandle,
    private val application: Application,
    val userModeHandler: UserModeHandler,
    getUserUpdatesUseCase: GetUserUpdatesUseCase

    ) : GeneralUpdatesViewModel<BranchDetailsEvent, BranchDetailsViewState, BranchDetailsState>(
    getUserUpdatesUseCase,
    getBranchFavouritesUpdates,
    getServicesUpdates
) {

    val branchId get() = viewStates?.branchId?.value

    private val branchDetailsExceptionHandler = exceptionHandler {
        viewStates?.isRefreshing?.value = false
        viewStates?.networkError?.value = true
    }

    init {
        startListenForUserUpdates()
        saveDataFromNavArg()

        source.onAddServiceToCartClicked = {
            setEvent(BranchDetailsEvent.AddServiceToCart(it))
        }
    }

    override fun handleEvents(event: BranchDetailsEvent) {
        when (event) {
            is BranchDetailsEvent.SelectSpecialty -> {
                handleSelectedSpeciality(event.id)
            }
            BranchDetailsEvent.RefreshScreen -> {
                branchId?.let { getData() }
            }
            BranchDetailsEvent.FilterClicked -> {
                viewStates?.updateFilterData?.value = true
                setState { BranchDetailsState.OpenFilter }
            }
            is BranchDetailsEvent.AddServiceToCart -> {
                toggleUpdatableItem(event.service.toDomain(), false)
            }
            is BranchDetailsEvent.ToggleBranchFavoriteClicked -> {
                toggleUpdatableItem(event.item.toDomainModel(), event.item.isFavorite)
            }
            BranchDetailsEvent.AllInfoClicked -> {
                setState { viewStates?.branchId?.value?.let { BranchDetailsState.OpenAllInfo(it) } }
            }
            BranchDetailsEvent.AllReviewsClicked -> {
                setState { branchId?.let { BranchDetailsState.OpenAllReviews(it) } }
            }
            BranchDetailsEvent.BackClicked -> {
                viewStates?.isBackToHome?.value = true
                setState { BranchDetailsState.FinishScreen }
            }
            BranchDetailsEvent.CartClicked -> {
                setState { BranchDetailsState.OpenCart }
            }
            BranchDetailsEvent.NotificationClicked -> {
                setState { BranchDetailsState.OpenNotificationsList }
            }
            BranchDetailsEvent.RefreshServices -> {
                getServicesList()
            }
            BranchDetailsEvent.CloseFilterClicked -> {
                setState { BranchDetailsState.HideFilter }
            }

            is BranchDetailsEvent.SubmitFilterClicked -> {
                viewStates?.filteredOfferedLocation?.value = event.list?.filter { it.isChecked.value }?.map {
                    (it as OfferedLocationsUIModel).toDomain(application.baseContext)
                }?.getOfferedLocationFilterKey()
                getServicesList()
            }
        }
    }

    private fun saveDataFromNavArg() {
        BranchDetailsScreenDestination.argsFrom(savedStateHandle).specialityId?.let {
            viewStates?.specialtyId = it
        }

        saveBranchIdFromNavArg()

    }

    private fun saveBranchIdFromNavArg() {
        val branchId = BranchDetailsScreenDestination.argsFrom(savedStateHandle).branchId
        viewStates?.branchId?.value = branchId
        getData()
    }

    private fun getData() {
        resetViewStates()
        viewModelScope.launch(branchDetailsExceptionHandler) {
            viewStates?.isRefreshing?.value = true
            setLoadingWithShimmer()
            val requests =
                setOf(
                    async { getBranchDetails() },
                    async { getServicesData() },

                    )
            requests.forEach {
                it.await()

            }
            setDoneLoading()
        }
    }

    private suspend fun getBranchDetails() {
        val branch = branchId?.let { getBranchDetailsUseCase.execute(it) }
        viewStates?.branchDetails?.value = branch?.toUI(onFavoriteClick = {
            setEvent(BranchDetailsEvent.ToggleBranchFavoriteClicked(it))
        })

    }

    private suspend fun getServicesData() {
        getSpecialtiesList()
        getServicesList()
    }


    private suspend fun getSpecialtiesList() {

        val specialtiesList = getSpecialtiesUseCase.execute(branchId).map {
            it.toUI()
        }

        if (specialtiesList.isNotEmpty()) {
            val selectedSpecialty: SpecialtiesUIModel = if (viewStates?.specialtyId == 0) {
                specialtiesList[0]
            } else {
                specialtiesList.find { it.id == viewStates?.specialtyId } ?: specialtiesList[0]
            }
            selectedSpecialty.isChecked.value = true
            viewStates?.specialtiesList?.clear()
            viewStates?.specialtiesList?.addAll(specialtiesList)
            viewStates?.selectedSpecialty?.value = selectedSpecialty
        }
        else {
            viewStates?.specialtiesList?.clear()
        }
    }


    private fun handleSelectedSpeciality(specialtyId: Int) {
        if (specialtyId != viewStates?.selectedSpecialty?.value?.id) {
            viewStates?.specialtyId = specialtyId
//
            source.refreshAll()

            val specialtiesList = viewStates?.specialtiesList

            val oldSelectedDateIndex = specialtiesList?.indexOfFirst { it.isChecked.value }
            val newSelectedDateIndex = specialtiesList?.indexOfFirst { it.id == specialtyId }

            oldSelectedDateIndex?.let {
                specialtiesList[oldSelectedDateIndex].isChecked.value = false
            }
            newSelectedDateIndex?.let {
                val updatedSpecialty = specialtiesList[newSelectedDateIndex]
                updatedSpecialty.isChecked.value = true
                specialtiesList[newSelectedDateIndex] = updatedSpecialty
                viewStates?.selectedSpecialty?.value = updatedSpecialty
            }


            getServicesList()

        }
    }

    private fun getServicesList() {
        source.specialityId = viewStates?.selectedSpecialty?.value?.id
        source.branchId = branchId
        source.offeredLocation = viewStates?.filteredOfferedLocation?.value
        source.refreshAll()
    }

    override fun updateItem(item: BaseUpdatableItem) {
        when (item) {
            is BranchDomainModel -> {
                val branch = viewStates?.branchDetails?.value
                viewStates?.branchDetails?.value = branch?.copy(isFavorite = item.isFavorite)
            }

            is ServiceDomainModel -> {
                val list = source.getCurrentList()
                val currentSession = list.find { it.uniqueId == item.uniqueId }
                currentSession?.isAdded?.value = item.isAdded ?: false
            }
        }
    }

    override fun resetViewStates() {
        viewStates?.networkError?.value = false
        viewStates?.specialtiesList?.clear()
        viewStates?.specialtiesList?.addAll(SpecialtiesUIModel.getShimmerList())

    }
    override fun createInitialViewState(): BranchDetailsViewState {
        return BranchDetailsViewState()
    }

}
