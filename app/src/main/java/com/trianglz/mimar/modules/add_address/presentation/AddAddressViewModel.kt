package com.trianglz.mimar.modules.add_address.presentation

import android.app.Application
import android.location.Location
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.google.android.libraries.maps.model.LatLng
import com.trianglz.mimar.common.presentation.base.MimarBaseViewModel
import com.trianglz.mimar.modules.add_address.presentation.contract.AddAddressEvent
import com.trianglz.mimar.modules.add_address.presentation.contract.AddAddressState
import com.trianglz.mimar.modules.add_address.presentation.contract.AddAddressViewState
import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel
import com.trianglz.mimar.modules.addresses.domain.model.EditAddAddressDomainModel
import com.trianglz.mimar.modules.addresses.domain.usecase.CreateAddressUseCase
import com.trianglz.mimar.modules.addresses.domain.usecase.EditAddressUseCase
import com.trianglz.mimar.modules.addresses.ui.mapper.toUI
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.destinations.AddAddressScreenAuthDestination
import com.trianglz.mimar.modules.map.presentation.model.MapScreenMode
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.user.domain.usecase.SetUserUseCase
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel @Inject constructor(
    private val createAddressUseCase: CreateAddressUseCase,
    private val editAddressUseCase: EditAddressUseCase,
    private val setUserUseCase: SetUserUseCase,
    private val getUserUpdatesUseCase: GetUserUpdatesUseCase,
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    val userModeHandler: UserModeHandler,

) : MimarBaseViewModel<AddAddressEvent, AddAddressViewState, AddAddressState>(getUserUpdatesUseCase) {


    init {
        startListenForUserUpdates()
        saveDataFromNavArg()
    }
    override fun handleEvents(event: AddAddressEvent) {
        when (event) {
            AddAddressEvent.EditMapClicked -> {
                setState { AddAddressState.OpenMapScreen }
            }

            is AddAddressEvent.UpdateLocation -> {
                event.latLng?.let {
                    updateLocation(it)
                }
            }
            is AddAddressEvent.UpdateFetchedLocation -> {
                updateFetchedLocationFromMap(event.address)
            }

            AddAddressEvent.BackButtonClicked -> {
                setState { AddAddressState.ShowCancelAddEditDialog }
            }

            AddAddressEvent.SaveButtonClicked -> {
                if (viewStates?.mode?.value == MapScreenMode.AddAddress)
                    createAddress()
                else
                    editAddress()

            }
            AddAddressEvent.CloseScreen -> {
                setState { AddAddressState.FinishScreen }
            }
        }
    }

    private fun updateLocation(latLng: LatLng) {
        val location = Location("")
        location.latitude = latLng.latitude
        location.longitude = latLng.longitude
        viewStates?.location?.value = location
    }

    private fun updateFetchedLocationFromMap(address: CustomerAddressUIModel) {

        updateLocation(LatLng(address.lat ?: 0.0, address.lng ?: 0.0))
        viewStates?.countryName?.value?.textFieldValue?.value = TextFieldValue(
            address.country ?: ""
        )
        address.city?.let {
            viewStates?.city?.value?.textFieldValue?.value = TextFieldValue(address.city)
        }
        address.district?.let {
            viewStates?.district?.value?.textFieldValue?.value = TextFieldValue(address.district)
        }
        address.buildingNumber?.let {
            viewStates?.buildingNum?.value?.textFieldValue?.value = TextFieldValue(it)
        }
        address.title?.let {
            viewStates?.addressTitle?.value?.textFieldValue?.value = TextFieldValue(address.title.getString(application))
        }

        address.streetName?.let {
            viewStates?.streetName?.value?.textFieldValue?.value =
                TextFieldValue(address.streetName)
        }

        address.landmark?.let {
            viewStates?.landmarkNotes?.value?.textFieldValue?.value =
                TextFieldValue(address.landmark)
        }

        address.secondaryNum?.let {
            viewStates?.secondaryNum?.value?.textFieldValue?.value =
                TextFieldValue(address.secondaryNum)
        }
        if(viewStates?.addressId?.value == null) viewStates?.addressId?.value = address.id

    }

    override fun createInitialViewState(): AddAddressViewState {
        return AddAddressViewState()
    }

    private fun createAddress() {
        launchCoroutine {
            setLoading()
            val address = createAddressUseCase.execute(
                getEdiAddAddressModel()

            )
            saveIfDefaultAddress(address)
            setDoneLoading()
            if(viewStates?.fromHome?.value == true)
                 setState { AddAddressState.OpenAddressesScreen(null) }
            else
                setState { AddAddressState.OpenHome }

        }
    }

    private fun editAddress() {
        launchCoroutine {
            setLoading()
            val address = editAddressUseCase.execute(
                getEdiAddAddressModel()
            )
            saveIfDefaultAddress(address)
            setDoneLoading()
            setState { AddAddressState.OpenAddressesScreen(address.toUI(address.isDefault)) }
        }
    }

    private fun getEdiAddAddressModel() =
        EditAddAddressDomainModel(
            viewStates?.addressTitle?.value?.textFieldValue?.value?.text ?: "",
            viewStates?.city?.value?.textFieldValue?.value?.text ?: "",
            viewStates?.streetName?.value?.textFieldValue?.value?.text ?: "",
            viewStates?.buildingNum?.value?.textFieldValue?.value?.text ?: "",
            viewStates?.district?.value?.textFieldValue?.value?.text ?: "",
            viewStates?.secondaryNum?.value?.textFieldValue?.value?.text ?: "",
            viewStates?.landmarkNotes?.value?.textFieldValue?.value?.text ?: "",
            viewStates?.location?.value?.latitude ?: 0.0,
            viewStates?.location?.value?.longitude ?: 0.0,
            viewStates?.addressId?.value,
        )

    private suspend fun saveIfDefaultAddress(address: CustomerAddressDomainModel){
        val user = getUserUpdatesUseCase.execute().first()
        if (address.isDefault && !user.isSetCurrentLocation ) {
            setUserUseCase.execute(getUserUpdatesUseCase.execute().first().copy(
                defaultAddress = address,
            ))
        }
    }

    private fun saveDataFromNavArg(){

        val data = AddAddressScreenAuthDestination.argsFrom(savedStateHandle)

        viewStates?.mode?.value = data.mode
        viewStates?.fromHome?.value = data.fromHome
        data.addressInfo?.let {
            setEvent(AddAddressEvent.UpdateFetchedLocation(data.addressInfo))
        }
    }
}