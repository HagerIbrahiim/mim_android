package com.trianglz.mimar.modules.add_address.presentation.contract

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.android.libraries.maps.model.LatLng
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.map.presentation.model.MapScreenMode
import kotlinx.coroutines.flow.MutableStateFlow


sealed class AddAddressEvent : BaseEvent {
    object EditMapClicked : AddAddressEvent()
    data class UpdateLocation(val latLng: LatLng?) : AddAddressEvent()
    data class UpdateFetchedLocation(val address: CustomerAddressUIModel) : AddAddressEvent()
    object BackButtonClicked : AddAddressEvent()

    object SaveButtonClicked : AddAddressEvent()

    object CloseScreen: AddAddressEvent()
}

sealed class AddAddressState : BaseState {
    object OpenMapScreen : AddAddressState()
    object FinishScreen : AddAddressState()
    data class OpenAddressesScreen(val address: CustomerAddressUIModel?) : AddAddressState()
    object ShowCancelAddEditDialog: AddAddressState()
    object OpenHome: AddAddressState()

}

data class AddAddressViewState(
    val addressTitle: MutableState<TextFieldState> = mutableStateOf(TextFieldState()),
    val countryName: MutableState<TextFieldState> = mutableStateOf(TextFieldState()),
    val city: MutableState<TextFieldState> = mutableStateOf(TextFieldState()),
    val streetName: MutableState<TextFieldState> = mutableStateOf(TextFieldState()),
    val buildingNum: MutableState<TextFieldState> = mutableStateOf(TextFieldState()),
    val district: MutableState<TextFieldState> = mutableStateOf(TextFieldState()),
    val secondaryNum: MutableState<TextFieldState> = mutableStateOf(TextFieldState()),
    val landmarkNotes: MutableState<TextFieldState> = mutableStateOf(TextFieldState()),
    var location: MutableState<Location?> = mutableStateOf(null),
    var mode: MutableState<MapScreenMode> = mutableStateOf(MapScreenMode.AddAddress),
    var addressId: MutableState<Int?> = mutableStateOf(null),
    var fromHome: MutableState<Boolean?> = mutableStateOf(null),
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
) : BaseViewState