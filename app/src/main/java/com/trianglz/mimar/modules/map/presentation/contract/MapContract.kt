package com.trianglz.mimar.modules.map.presentation.contract

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.LatLng
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.map.presentation.model.MapScreenMode
import kotlinx.coroutines.flow.MutableStateFlow


sealed class MapEvent : BaseEvent {
    object CloseScreenClicked : MapEvent()
    object SavePickedLocation: MapEvent()
    object MoveToCurrentLocation: MapEvent()
    data class UpdateLocation(val latLng: LatLng?): MapEvent()
}

sealed class MapState : BaseState {
    object FinishScreen : MapState()
    data class SendLocationToPreviousScreen(val addressInfo: CustomerAddressUIModel): MapState()
}

data class MapViewState(
    val location:  MutableState<Location?> = mutableStateOf(null),
    val searchText:  MutableState<String?> = mutableStateOf(""),
    var cameraPosition: MutableState<CameraPosition?> = mutableStateOf(null),
    var screenMode: MutableState<MapScreenMode> = mutableStateOf(MapScreenMode.AddAddress),
    val selectBtnEnabled:  MutableState<Boolean> = mutableStateOf(true),
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
) : BaseViewState