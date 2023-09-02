package com.trianglz.mimar.modules.discover.presentation

import android.app.Application
import android.location.Location
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.core.presentation.helper.location.getLastLocationPermitted
import com.trianglz.core_compose.presentation.pagination.model.ComposePaginationModel
import com.trianglz.mimar.common.presentation.base.GeneralUpdatesViewModel
import com.trianglz.mimar.common.presentation.extensions.checkIfLocationGranted
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchFavouritesUpdates
import com.trianglz.mimar.modules.branches.presentation.mapper.toDomainModel
import com.trianglz.mimar.modules.branches.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.branches.presentation.source.BranchesSource
import com.trianglz.mimar.modules.categories.domain.usecase.GetCategoriesUseCase
import com.trianglz.mimar.modules.categories.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel
import com.trianglz.mimar.modules.destinations.DiscoverScreenDestination
import com.trianglz.mimar.modules.discover.presentation.contract.DiscoverEvent
import com.trianglz.mimar.modules.discover.presentation.contract.DiscoverState
import com.trianglz.mimar.modules.discover.presentation.contract.DiscoverViewState
import com.trianglz.mimar.modules.filter.presenation.model.BranchesFilterUIModel
import com.trianglz.mimar.modules.user.domain.usecase.CheckUserIsLoggedInUseCase
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.user.domain.usecase.SetUserUseCase
import com.trianglz.mimar.modules.user.presentaion.mapper.toDomain
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    getBranchFavouritesUpdates: GetBranchFavouritesUpdates,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    val source: BranchesSource,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val isLoggedInUseCase: CheckUserIsLoggedInUseCase,
    private val fusedLocation: FusedLocationProviderClient,
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    val userModeHandler: UserModeHandler,
    private val setUserUseCase: SetUserUseCase,

    ) : GeneralUpdatesViewModel<DiscoverEvent, DiscoverViewState, DiscoverState>(
    getUserUpdatesUseCase,
    getBranchFavouritesUpdates
) {

//    val user = getUserUpdatesUseCase.execute().map { it.toUIModel() }

    private val userValue get() = viewStates?.user?.value
    private val location get() = viewStates?.location?.value


    private val categoriesExceptionHandler = exceptionHandler {
        viewStates?.categories?.clear()
        viewStates?.networkError?.value = true
    }

    private val categories
        get() = viewModelScope.launch(categoriesExceptionHandler, start = CoroutineStart.LAZY) {
            getCategories()
        }

    init {
        launchCoroutine {

            saveDataFromNavArg()
            fillSourceData()
            resetViewStates()
            if (isLoggedInUseCase.execute()) {
                startListenForUserUpdates()
            } else {
                getData()
            }
        }
    }

    private fun saveDataFromNavArg(){
        viewStates?.selectedCategoryId = DiscoverScreenDestination.argsFrom(savedStateHandle).categoryId ?: 0
    }
    private fun fillSourceData() {
        source.onFavoriteClick = {
            setEvent(DiscoverEvent.ToggleBranchFavoriteClicked(it))
        }
        source.onClick = { id ->
            setEvent(DiscoverEvent.ItemBranchClicked(id))
        }
    }

    private fun getData() {
//        getUserData()
        launchCoroutine {
            categories.join()
        }

    }

//    private fun getUserData() {
//        launchCoroutine {
//            user.collect { user ->
//                viewStates?.selectedLocationId?.value = user.defaultAddress?.id
//                viewStates?.user?.value = user
//                setUserLocation { false }
//            }
//        }
//    }

    private fun getBranches() {
        if (viewStates?.location?.value == null) {
            checkUserLocation()
        }
        prepareSourceInfo()
        source.refreshAll()

    }

    private fun prepareSourceInfo() {
        viewStates?.apply {
            source.lat = location.value?.latitude
            source.lng = location.value?.longitude
            source.categoryId = selectedCategory.value?.uniqueId
        }

    }


    private suspend fun getCategories() {
        val categories = getCategoriesUseCase.execute(-1)
        val allCategories = categories.map {
            it.toUIModel ()
        }

        if (allCategories.isNotEmpty()) {
            val selectedCategory: CategoryUIModel = if (viewStates?.selectedCategoryId == 0) {
                allCategories[0]
            } else {
                allCategories.find { it.id == viewStates?.selectedCategoryId } ?: allCategories[0]
            }
            selectedCategory.isChecked.value = true
            viewStates?.categories?.clear()
            viewStates?.categories?.addAll(allCategories)
            viewStates?.selectedCategory?.value = selectedCategory
        }
        getBranches()
    }


    private fun handleSelectedCategory(selectedCategoryId: Int) {
        if (selectedCategoryId != viewStates?.selectedCategory?.value?.id) {
            viewStates?.selectedCategoryId = selectedCategoryId
            val categoriesList = viewStates?.categories

            val oldSelectedDateIndex = categoriesList?.indexOfFirst { it.isChecked.value }
            val newSelectedDateIndex = categoriesList?.indexOfFirst { it.id == selectedCategoryId }

            oldSelectedDateIndex?.let {
                if(it >= 0) categoriesList[oldSelectedDateIndex].isChecked.value = false
            }
            newSelectedDateIndex?.let {
                if(it >= 0) {
                    val updatedSpecialty = categoriesList[newSelectedDateIndex]
                    categoriesList[newSelectedDateIndex].isChecked.value = true
                    viewStates?.selectedCategory?.value = updatedSpecialty
                }
            }


            getBranches()

        }
    }

    private fun checkUserLocation() {
        setUserLocation {
            val result = application.checkIfLocationGranted()
            if (result.not()) {
                setState { DiscoverState.AskForLocationPermission }
            }
            return@setUserLocation result
        }

    }

    private fun locationPermissionGranted() {
//        viewStates?.locationEnabled?.value = true
        updateUserDefaultAddress()
        getBranches()
    }

    private fun setUserLocation(checkPermissionAction: () -> Boolean) {
        launchCoroutine {
            if (isLoggedInUseCase.execute()) {
                if (userValue?.defaultAddress != null) {
                    val location = Location("")
                    userValue?.defaultAddress?.lat?.let { location.latitude = it }
                    userValue?.defaultAddress?.lng?.let { location.longitude = it }
                    setLocation(location)
                    return@launchCoroutine
                }
            }
            if (checkPermissionAction()) {
                setLocation(fusedLocation.getLastLocationPermitted())
            }
        }

    }

    private fun setLocation(location: Location) {
        viewStates?.location?.value = location
        getBranches()
    }

    override fun createInitialViewState(): DiscoverViewState {
        return DiscoverViewState()
    }

    override fun handleEvents(event: DiscoverEvent) {
        when (event) {
            is DiscoverEvent.SelectCategory -> {
                handleSelectedCategory(event.id)
            }
            is DiscoverEvent.ItemBranchClicked -> {
                setState { DiscoverState.OpenBranchDetails(event.id) }
            }
            is DiscoverEvent.ToggleBranchFavoriteClicked -> {
                toggleUpdatableItem(event.item.toDomainModel(), event.item.isFavorite)
            }
            DiscoverEvent.OnSearchClicked -> {
                viewStates?.location?.value?.let {
                    setState { DiscoverState.OpenSearchScreen(it) }
                }?: run {
                    setState { DiscoverState.AskForLocationPermission }
                }
            }
            DiscoverEvent.RefreshScreen -> {
                resetViewStates()
                getData()
            }
            DiscoverEvent.LocationPermissionGrantedSuccessfully -> {
                locationPermissionGranted()
            }
            DiscoverEvent.OnRequestLocationClicked -> {
                setState { DiscoverState.AskForLocationPermission }
            }
            DiscoverEvent.OnChangeAddressClicked -> {
                setState { DiscoverState.OpenLocationBottomSheet }
            }
            DiscoverEvent.RefreshBranches -> {
                getBranches()
            }
            is DiscoverEvent.OnFilterClicked -> {
                setState { DiscoverState.OpenFilterDialog(viewStates?.branchesFilter?.value) }
            }
            DiscoverEvent.BottomSheetAddNewAddressClicked -> {
                setState { DiscoverState.OpenAddAddressScreen }
            }
            DiscoverEvent.CloseBLocationBottomSheetClicked -> {
                setState { DiscoverState.HideLocationBottomSheet }
            }
            is DiscoverEvent.ApplyFilter -> {
                applyFilter(event.filter)
            }
        }
    }

    override fun resetViewStates() {
        viewStates?.networkError?.value = false
        viewStates?.categories?.clear()
        viewStates?.categories?.addAll(CategoryUIModel.getShimmerList())

    }

    override fun updateItem(item: BaseUpdatableItem) {
        when (item) {
            is BranchDomainModel -> {
                val list = source.getCurrentList().toMutableList()

                val currentItem = list.find { it.uniqueId == item.uniqueId }

                val currentIndex = list.indexOf(currentItem)

                val updatedBranchItem = item.toUIModel(onFavoriteClick = { branch ->
                    setEvent(DiscoverEvent.ToggleBranchFavoriteClicked(branch))
                }, onClick = { id ->
                    setEvent(DiscoverEvent.ItemBranchClicked(id))
                })
                currentIndex.let {
                    source.updateItem(
                        ComposePaginationModel.UpdateAction.Update(true),
                        updatedBranchItem,
                        it,
                    )
                }
            }
        }

    }

    private fun applyFilter(filter: BranchesFilterUIModel){
        viewStates?.branchesFilter?.value = filter
        source.apply {
            specialties = filter.specialties
            servicedGenders = filter.genderList
            offeredLocations = filter.offeredLocations
            rating = filter.selectedRating
            availableDate = filter.pickedDate
            availableTime = filter.pickedTime
        }
        getBranches()
    }

    override fun userUpdates(user: UserUIModel) {
        super.userUpdates(user)
        viewStates?.user?.value = user
        if (viewStates?.selectedLocationId?.value != user.defaultAddress?.id) {
            viewStates?.selectedLocationId?.value = user.defaultAddress?.id
            setUserLocation { false }
            getData()
        } else {
            if (user.defaultAddress?.id == null) {
                getData()
            }
        }
    }
//    @SuppressLint("MissingPermission")
    private fun updateUserDefaultAddress(){
        launchCoroutine {
            val currentLocation = fusedLocation.getLastLocationPermitted()
            val user = viewStates?.user?.value?.copy(
                defaultAddress = CustomerAddressUIModel.getCurrentLocationItem(
                    isDefault = true,
                    lat = currentLocation.latitude,
                    lng = currentLocation.longitude,
                ),
                isSetCurrentLocation = true,
            )
            user?.toDomain(application.baseContext)?.let { setUserUseCase.execute(it) }

        }
    }
}
