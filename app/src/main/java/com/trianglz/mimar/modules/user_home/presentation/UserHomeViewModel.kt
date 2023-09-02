package com.trianglz.mimar.modules.user_home.presentation

import android.app.Application
import android.location.Location
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.core.presentation.helper.location.getLastLocationPermitted
import com.trianglz.mimar.common.presentation.base.GeneralUpdatesViewModel
import com.trianglz.mimar.common.presentation.extensions.checkIfLocationGranted
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.appointments.domain.usecase.GetLastAppointmentUseCase
import com.trianglz.mimar.modules.appointments.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentUIModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchFavouritesUpdates
import com.trianglz.mimar.modules.branches.domain.usecase.GetFavoriteBranchesUseCase
import com.trianglz.mimar.modules.branches.domain.usecase.GetPopularBranchesUseCase
import com.trianglz.mimar.modules.branches.presentation.mapper.toDomainModel
import com.trianglz.mimar.modules.branches.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.categories.domain.usecase.GetCategoriesUseCase
import com.trianglz.mimar.modules.categories.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.categories.presentation.model.CategoryUIModel
import com.trianglz.mimar.modules.user.domain.usecase.CheckUserIsLoggedInUseCase
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.user.domain.usecase.SetUserUseCase
import com.trianglz.mimar.modules.user.presentaion.mapper.toDomain
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import com.trianglz.mimar.modules.user_home.presentation.contract.UserHomeEvent
import com.trianglz.mimar.modules.user_home.presentation.contract.UserHomeState
import com.trianglz.mimar.modules.user_home.presentation.contract.UserHomeViewState
import com.trianglz.mimar.modules.user_home.presentation.model.*
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserHomeViewModel @Inject constructor(
    getBranchFavouritesUpdates: GetBranchFavouritesUpdates,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getFavoriteBranchesUseCase: GetFavoriteBranchesUseCase,
    private val getPopularBranchesUseCase: GetPopularBranchesUseCase,
    private val isLoggedInUseCase: CheckUserIsLoggedInUseCase,
    private val getLastAppointmentUseCase: GetLastAppointmentUseCase,
    private val fusedLocation: FusedLocationProviderClient,
    private val application: Application,
    val userModeHandler: UserModeHandler,
    private val setUserUseCase: SetUserUseCase,
) : GeneralUpdatesViewModel<UserHomeEvent, UserHomeViewState, UserHomeState>(
    getUserUpdatesUseCase,
    getBranchFavouritesUpdates
) {

//    val user = getUserUpdatesUseCase.execute().map { it.toUIModel() }

    private val userValue get() = viewStates?.user?.value
    private val location get() = viewStates?.location?.value

    private val lastAppointmentExceptionHandler = exceptionHandler {
        viewStates?.lastAppointment?.value = null
        viewStates?.lastAppointmentError?.value = true
    }

    private val categoriesExceptionHandler = exceptionHandler {
        viewStates?.categories?.value = null
        viewStates?.ourCategoriesError?.value = true
    }

    private val favoriteBranchesExceptionHandler = exceptionHandler {
        viewStates?.favoriteBranches?.value = null
        viewStates?.favoriteBranchesError?.value = true
    }

    private val popularBranchesExceptionHandler = exceptionHandler {
        viewStates?.popularBranches?.value = null
        viewStates?.popularBranchesError?.value = true
    }
    private val categories
        get() = viewModelScope.launch(categoriesExceptionHandler, start = CoroutineStart.LAZY) {
            getCategories()
        }

    private val favoriteBranches
        get() = viewModelScope.launch(
            favoriteBranchesExceptionHandler,
            start = CoroutineStart.LAZY
        ) {
            getFavoriteBranches()
        }

    private val popularBranches
        get() = viewModelScope.launch(
            popularBranchesExceptionHandler,
            start = CoroutineStart.LAZY
        ) {
            getPopularBranches()
        }

    init {
        launchCoroutine {
            resetViewStates()
//            checkUserLocation()
            if (isLoggedInUseCase.execute()) {
                startListenForUserUpdates()
            } else {
                getData()
            }

        }
    }

    private fun handleGuestMode() {
        launchCoroutine {
//            if (isLoggedInUseCase.execute()) {
//                viewStates?.guestWelcomeMessage?.value = GuestWelcomeSectionUIModel(isLoggedInUseCase.execute().not())
            viewStates?.guestWelcomeMessage?.value = GuestWelcomeSectionUIModel(true)
//            }
        }

    }

    private fun checkLastAppointment() {
        viewModelScope.launch(lastAppointmentExceptionHandler) {
            val lastAppointment = getLastAppointmentUseCase.execute()?.toUIModel {
                setEvent(UserHomeEvent.OnLastAppointmentClicked(it))
            }
            viewStates?.lastAppointment?.value =
                LastAppointmentSectionUIModel(appointment = lastAppointment)
        }
    }

    private fun getData() {
//        getUserData()
        launchCoroutine {
            if (isLoggedInUseCase.execute()) {
                checkLastAppointment()
            } else {
                viewStates?.lastAppointment?.value = null
                handleGuestMode()
            }
        }

        launchCoroutine {
            categories.join()
        }
        getBranches()

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

        } else {
//            launchCoroutine {
//                categories.join()
//            }
            launchCoroutine {
                if (isLoggedInUseCase.execute()) {
                    favoriteBranches.join()
                } else {
                    viewStates?.favoriteBranches?.value = null
                }
            }
            launchCoroutine {
                popularBranches.join()
            }
        }
    }


    private suspend fun getCategories() {
        val categories = getCategoriesUseCase.execute()
        val allCategories = categories.mapIndexed { index, item ->
            item.toUIModel(index = index)
        }
        viewStates?.categories?.value = null
        viewStates?.categories?.value = CategoriesSectionUIModel(allCategories, onSeeMoreClicked = {
            setEvent(UserHomeEvent.CategoriesSeeMoreClicked)
        })
    }

    private suspend fun getFavoriteBranches() {
        viewStates?.location?.value?.let {
            val branches = getFavoriteBranchesUseCase.execute()
            val allBranches = branches.map {
                it.toUIModel(onFavoriteClick = { id ->
                    setEvent(UserHomeEvent.ToggleBranchFavoriteClicked(id))
                }, onClick = { id ->
                    setEvent(UserHomeEvent.ItemBranchClicked(id))
                })
            }
            viewStates?.favoriteBranches?.value = null
            viewStates?.favoriteBranches?.value = BranchesSectionUIModel(
                allBranches.toMutableStateList(),
                BranchSectionType.Favorites,
                onSeeMoreClicked = {
                    setEvent(UserHomeEvent.FavoritesSeeMoreClicked)
                })
        }

    }

    private suspend fun getPopularBranches() {
        viewStates?.location?.value?.let {

            val branches = getPopularBranchesUseCase.execute(lat = it.latitude, lng = it.longitude)
            val allBranches = branches.map {
                it.toUIModel(onFavoriteClick = { id ->
                    setEvent(UserHomeEvent.ToggleBranchFavoriteClicked(id))
                }, onClick = { id ->
                    setEvent(UserHomeEvent.ItemBranchClicked(id))
                })
            }
            viewStates?.popularBranches?.value = null
            viewStates?.popularBranches?.value = BranchesSectionUIModel(
                allBranches.toMutableStateList(),
                BranchSectionType.Popular,
                onSeeMoreClicked = {
                    setEvent(UserHomeEvent.PopularProvidersSeeMoreClicked)
                })
        }
    }

    private fun checkUserLocation() {
        setUserLocation {
            val result = application.checkIfLocationGranted()
            if (result.not()) {
                viewStates?.popularBranches?.value = null
                viewStates?.popularBranches?.value = BranchesSectionUIModel(
                    SnapshotStateList(), BranchSectionType.Popular,
                )
                viewStates?.favoriteBranches?.value = null
                setState { UserHomeState.AskForLocationPermission }
            }
            return@setUserLocation result
        }

    }


    private fun resetBranchesViews() {
        resetFavoritesViews()
        resetPopularViews()
    }

    private fun resetFavoritesViews() {
        viewStates?.favoriteBranches?.value = null
        viewStates?.favoriteBranches?.value =
            BranchesSectionUIModel(
                BranchUIModel.getShimmerList(),
                branchSectionType = BranchSectionType.Favorites,
                showShimmer = true
            )
    }

    private fun resetPopularViews() {
        viewStates?.popularBranches?.value = null
        viewStates?.popularBranches?.value = BranchesSectionUIModel(
            BranchUIModel.getShimmerList(),
            branchSectionType = BranchSectionType.Popular,
            showShimmer = true
        )
    }

    private fun locationPermissionGranted() {
//        viewStates?.locationEnabled?.value = true
        updateUserDefaultAddress()
        resetBranchesViews()
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
                updateUserDefaultAddress()
            }
        }
        // }
    }

    private fun setLocation(location: Location) {
        viewStates?.location?.value = location
        getBranches()
    }

    override fun createInitialViewState(): UserHomeViewState {
        return UserHomeViewState()
    }

    override fun handleEvents(event: UserHomeEvent) {
        when (event) {
            UserHomeEvent.CategoriesSeeMoreClicked -> {
                setState { UserHomeState.OpenAllCategories }
            }
            UserHomeEvent.FavoritesSeeMoreClicked -> {
                setState { UserHomeState.OpenAllFavorites }
            }
            is UserHomeEvent.ItemCategoryClicked -> {
                setState { UserHomeState.OpenCategoryDetails(event.id) }
            }
            is UserHomeEvent.ItemBranchClicked -> {
                setState { UserHomeState.OpenBranchDetails(event.id) }
            }
            is UserHomeEvent.ToggleBranchFavoriteClicked -> {
                toggleUpdatableItem(event.item.toDomainModel(), event.item.isFavorite)
            }
            UserHomeEvent.OnGetStartedClicked -> {
                setState { UserHomeState.OpenSignupScreen }
            }
            is UserHomeEvent.OnLastAppointmentClicked -> {
                setState { UserHomeState.OpenAppointmentDetails(event.id) }
            }
            UserHomeEvent.OnSearchClicked -> {
                viewStates?.location?.value?.let {
                    setState { UserHomeState.OpenSearchScreen(it) }
                } ?: run {
                    setState { UserHomeState.AskForLocationPermission }
                }
            }
            UserHomeEvent.PopularProvidersSeeMoreClicked -> {
                setState { UserHomeState.OpenAllPopularProviders }
            }
            UserHomeEvent.RefreshScreen -> {
                resetViewStates()
                getData()
            }
            UserHomeEvent.LocationPermissionGrantedSuccessfully -> {
                locationPermissionGranted()
            }
            UserHomeEvent.OnRequestLocationClicked -> {
                setState { UserHomeState.AskForLocationPermission }
            }
            UserHomeEvent.OnChangeAddressClicked -> {
                setState { UserHomeState.OpenLocationBottomSheet }
            }
            UserHomeEvent.BottomSheetAddNewAddressClicked -> {
                setState { UserHomeState.OpenAddAddressScreen }
            }
            UserHomeEvent.CloseLocationBottomSheetClicked -> {
                setState { UserHomeState.HideLocationBottomSheet }
            }

        }
    }

    override fun resetViewStates() {
        setOf(
            viewStates?.ourCategoriesError,
            viewStates?.favoriteBranchesError,
            viewStates?.popularBranchesError,
            viewStates?.lastAppointmentError,
        ).forEach {
            it?.value = false
        }
        viewStates?.categories?.value = null
        viewStates?.categories?.value =
            CategoriesSectionUIModel(CategoryUIModel.getShimmerList(), true)
        viewStates?.lastAppointment?.value = null
        viewStates?.lastAppointment?.value = LastAppointmentSectionUIModel(AppointmentUIModel.getShimmerList(1).firstOrNull(), true)

        resetBranchesViews()
    }

    override fun updateItem(item: BaseUpdatableItem) {
        when (item) {
            is BranchDomainModel -> {
                updateBranches(item, list = viewStates?.popularBranches?.value?.list)
                updateBranches(
                    item,
                    list = viewStates?.favoriteBranches?.value?.list,
                    replaceItem = false
                ) {
                    val list = viewStates?.favoriteBranches?.value?.copy()
                    viewStates?.favoriteBranches?.value = null
                    viewStates?.favoriteBranches?.value = list
                }
            }
        }

    }

    private fun updateBranches(
        branch: BranchDomainModel,
        list: SnapshotStateList<BranchUIModel>?,
        replaceItem: Boolean = true,
        onlyItemAvailableAction: () -> Unit = {},
    ) {
        val updatedBranchUIModel = branch.toUIModel(onFavoriteClick = { item ->
            setEvent(UserHomeEvent.ToggleBranchFavoriteClicked(item))
        }, onClick = { id ->
            setEvent(UserHomeEvent.ItemBranchClicked(id))
        })
        val currentBranch = list?.find { it.id == updatedBranchUIModel.id }
        val currentIndex = list?.indexOf(currentBranch)
        if (currentIndex != null && currentIndex != -1) {
            list.removeAt(currentIndex)
            if (replaceItem) {
                list.add(currentIndex, updatedBranchUIModel)
            } else {
                if (list.size == 1) onlyItemAvailableAction()
            }
        }
        if (updatedBranchUIModel.isFavorite) {
            launchCoroutine {
                resetFavoritesViews()
                favoriteBranches.join()
            }
        }
    }

    override fun userUpdates(user: UserUIModel) {
        viewStates?.user?.value = user
        if (viewStates?.selectedLocationId?.value != user.defaultAddress?.id) {
            viewStates?.selectedLocationId?.value = user.defaultAddress?.id
            setUserLocation { false }
            getData()
        }else {
            if (user.defaultAddress?.id == null) {
                getData()
            }
        }

    }

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
