package com.trianglz.mimar.modules.addresses_list.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import kotlinx.coroutines.flow.MutableStateFlow

sealed class AddressesListEvent : BaseEvent {
    data class DeleteAddressItemIconClicked(val addressId: Int) : AddressesListEvent()
    data class EditAddressClicked(val address: CustomerAddressUIModel) : AddressesListEvent()
    data class ChangeDefaultAddress(val address: CustomerAddressUIModel) : AddressesListEvent()

    object CloseIconClicked: AddressesListEvent()
    object SkipButtonClicked: AddressesListEvent()
    object AddNewAddressClicked: AddressesListEvent()
    data class DeleteAddressDialogClicked(val addressId: Int) : AddressesListEvent()

    object RefreshScreen:  AddressesListEvent()
    data class UpdateAddress(val address: CustomerAddressUIModel) : AddressesListEvent()
    data class AddressCheckboxClicked(val address: CustomerAddressUIModel) : AddressesListEvent()
    object LocationPermissionGrantedSuccessfully : AddressesListEvent()

}

sealed class AddressesListState: BaseState {
    object FinishScreen: AddressesListState()
    object OpenHome: AddressesListState()
    data class OpenEditAddress(val address: CustomerAddressUIModel): AddressesListState()
    object OpenAddAddress: AddressesListState()
    data class ShowDeleteAddressDialog(val addressId: Int) : AddressesListState()

    data class ShowChangeDefaultAddressDialog(val address: CustomerAddressUIModel) : AddressesListState()
    object AskForLocationPermission : AddressesListState()

    object HideBottomSheet: AddressesListState()

    data class SendLocationToPreviousScreen(val address: CustomerAddressUIModel): AddressesListState()

}

data class AddressesListViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var user: MutableState<UserUIModel?> = mutableStateOf(null),
    val isConsumed: MutableState<Boolean> = mutableStateOf(false),

    ): BaseViewState