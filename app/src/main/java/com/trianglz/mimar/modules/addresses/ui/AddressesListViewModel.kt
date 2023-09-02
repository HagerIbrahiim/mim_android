package com.trianglz.mimar.modules.addresses.ui

import android.app.Application
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.trianglz.core.presentation.helper.location.getLastLocationPermitted
import com.trianglz.core_compose.presentation.pagination.model.ComposePaginationModel
import com.trianglz.mimar.common.presentation.base.MimarBaseViewModel
import com.trianglz.mimar.modules.addresses.domain.usecase.ChangeDefaultAddressUseCase
import com.trianglz.mimar.modules.addresses.domain.usecase.DeleteAddressUseCase
import com.trianglz.mimar.modules.addresses.ui.mapper.toUI
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.addresses_list.presentation.contract.AddressesListEvent
import com.trianglz.mimar.modules.addresses_list.presentation.contract.AddressesListEvent.*
import com.trianglz.mimar.modules.addresses_list.presentation.contract.AddressesListState
import com.trianglz.mimar.modules.addresses_list.presentation.contract.AddressesListState.*
import com.trianglz.mimar.modules.addresses_list.presentation.contract.AddressesListViewState
import com.trianglz.mimar.modules.addresses_list.presentation.model.AddressStickyHeader
import com.trianglz.mimar.modules.addresses_list.presentation.source.AddressesListSource
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.user.domain.usecase.SetUserUseCase
import com.trianglz.mimar.modules.user.presentaion.mapper.toDomain
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressesListViewModel @Inject constructor(
    private val changeDefaultAddressUseCase: ChangeDefaultAddressUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
    val source: AddressesListSource,
    private val setUserUseCase: SetUserUseCase,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    private val fusedLocation: FusedLocationProviderClient,
    private val application: Application,
    val userModeHandler: UserModeHandler,
    ) : MimarBaseViewModel<AddressesListEvent, AddressesListViewState, AddressesListState>(getUserUpdatesUseCase) {

    init {
        startListenForUserUpdates()
        getAddressesList()
    }

    override fun handleEvents(event: AddressesListEvent) {
        when(event){
            is DeleteAddressItemIconClicked -> {
                 setState { ShowDeleteAddressDialog(event.addressId) }
            }
            is ChangeDefaultAddress -> {
                handleChangeDefaultAddress(event.address.id)
            }
            CloseIconClicked -> {
                setState { FinishScreen }
            }
            is EditAddressClicked -> {
                setState { OpenEditAddress(event.address) }
            }
            SkipButtonClicked -> {
                setState { OpenHome }
            }
            AddNewAddressClicked -> {
                setState { OpenAddAddress }
            }

            is DeleteAddressDialogClicked -> {
                deleteAddress(event.addressId)
            }
            RefreshScreen -> getAddressesList()
            is AddressCheckboxClicked ->{
                if(event.address != getCurrentCheckedAddress())
                setState { ShowChangeDefaultAddressDialog(event.address) }
            }

            LocationPermissionGrantedSuccessfully -> {
                handleGrantedLocation()
            }
            is UpdateAddress -> {
                updateAddress(event.address)
            }
        }
    }

    private fun getAddressesList() {
        source.refreshAll()
    }

    private fun handleGrantedLocation(){
        launchCoroutine {
            val currentLocation = fusedLocation.getLastLocationPermitted()
            updateUserDefaultAddress(
                CustomerAddressUIModel.getCurrentLocationItem(
                    lat = currentLocation.latitude,
                    lng = currentLocation.longitude,
                    isDefault = true,
                ), isSetCurrentLocation = true
            )
        }
    }
    private fun changeDefaultAddress(addressId: Int) {
        launchCoroutine {
            if(getCurrentCheckedAddress().id != addressId){
                setLoading()
                val address = changeDefaultAddressUseCase.execute(addressId).toUI()
                updateUserDefaultAddress(address)
                setDoneLoading()
                source.refreshAll()
            }
            setState { HideBottomSheet }
        }
    }

    private fun getCurrentCheckedAddress() = source.getCurrentList().filter { it !is AddressStickyHeader }.find {
        (it as CustomerAddressUIModel).isChecked
    } as CustomerAddressUIModel
    private fun handleChangeDefaultAddress(addressId: Int){

        when (addressId) {
            -1 -> {
                setState { AskForLocationPermission }
            }
            else -> {
                changeDefaultAddress(addressId)
            }
        }

    }
    private suspend fun updateUserDefaultAddress(address: CustomerAddressUIModel? = null, isSetCurrentLocation: Boolean = false){
        val user = viewStates?.user?.value?.copy(
            defaultAddress = address,
            isSetCurrentLocation = isSetCurrentLocation,
        )
        launchCoroutine {
            user?.toDomain(application.baseContext)?.let {
                setUserUseCase.execute(it)
            }
        }

    }

    private fun deleteAddress(addressId: Int) {
        launchCoroutine {
            setLoading()
            deleteAddressUseCase.execute(addressId)
            setDoneLoading()
            source.refreshAll()
        }
    }

    private fun updateAddress(address: CustomerAddressUIModel){
        val list = source.dataListValue
        val index = list?.indexOfFirst { it.uniqueId == address.id } ?: -1
        if(index > -1 ) {
            source.updateItem(
                ComposePaginationModel.UpdateAction.Update(true),
                address,
                index,
            )
        }

    }

    override fun createInitialViewState(): AddressesListViewState {
        return AddressesListViewState()
    }

    override fun userUpdates(user: UserUIModel) {
        viewStates?.user?.value = user
    }
}