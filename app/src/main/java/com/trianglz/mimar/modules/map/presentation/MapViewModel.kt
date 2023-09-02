package com.trianglz.mimar.modules.map.presentation

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.libraries.maps.model.LatLng
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.helper.location.getLastLocationPermitted
import com.trianglz.mimar.modules.addresses.domain.usecase.FetchAddressInfoFromMap
import com.trianglz.mimar.modules.addresses.ui.mapper.toUI
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.map.presentation.contract.MapEvent
import com.trianglz.mimar.modules.map.presentation.contract.MapEvent.*
import com.trianglz.mimar.modules.map.presentation.contract.MapState
import com.trianglz.mimar.modules.map.presentation.contract.MapViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val fusedLocation: FusedLocationProviderClient,
    private val fetchAddressInfoFromMap: FetchAddressInfoFromMap
) : BaseMVIViewModel<MapEvent, MapViewState, MapState>() {

    private fun updateLocation(latLng: LatLng?) {

        latLng?.let {
            if (it.latitude != viewStates?.location?.value?.latitude) {
                val location = Location("")
                location.latitude = it.latitude
                location.longitude = it.longitude
                setLocation(location)
            }
        } ?: run {
            setUserLocation()
        }
    }

    private fun setUserLocation() {
        launchCoroutine {
            setLocation(fusedLocation.getLastLocationPermitted())
        }
    }

    private fun setLocation(loc: Location) {
        viewStates?.location?.value = loc
    }


    override fun createInitialViewState(): MapViewState {
        return MapViewState()
    }


    override fun handleEvents(event: MapEvent) {
        when (event) {
            CloseScreenClicked -> {
                setState { MapState.FinishScreen }
            }
            MoveToCurrentLocation -> {
                setUserLocation()
            }
            SavePickedLocation -> {
                fetchLocationInfoFromLatLong()
            }
            is UpdateLocation -> {
                updateLocation(event.latLng)
            }
        }
    }

    private fun fetchLocationInfoFromLatLong() {

        val selectedLocation = viewStates?.cameraPosition?.value?.target ?: return

        launchCoroutine {
            setLoading()
            val data = fetchAddressInfoFromMap.execute(
                selectedLocation.latitude, selectedLocation.longitude
            ).toUI()

            setDoneLoading()
            setState { MapState.SendLocationToPreviousScreen(CustomerAddressUIModel(
                0, city = data.city, country = data.country, lat = selectedLocation.latitude,
                lng = selectedLocation.longitude, district = data.region
            )) }
        }
    }
}