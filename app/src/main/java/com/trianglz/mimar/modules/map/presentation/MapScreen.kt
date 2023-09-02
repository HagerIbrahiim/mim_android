package com.trianglz.mimar.modules.map.presentation


import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.places.widget.Autocomplete
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.presentation.helper.startGooglePlacesActivity
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.destinations.AddAddressScreenAuthDestination
import com.trianglz.mimar.modules.destinations.AddAddressScreenMainDestination
import com.trianglz.mimar.modules.map.presentation.composables.MapScreenContent
import com.trianglz.mimar.modules.map.presentation.contract.MapEvent
import com.trianglz.mimar.modules.map.presentation.contract.MapState
import com.trianglz.mimar.modules.map.presentation.model.MapScreenMode


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapScreen(
    navigator: DestinationsNavigator? = null,
    resultNavigator: ResultBackNavigator<CustomerAddressUIModel>,
    previousScreenRoute: String?= null,
    latLng: LatLng? = null,
    mode: MapScreenMode = MapScreenMode.AddAddress,
    fromAddAddress: Boolean = false,
    fromHome: Boolean = false,
    viewModel: MapViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.setEvent(MapEvent.UpdateLocation(latLng))
    }

    val currentLocation = remember {
        viewModel.viewStates?.location
    }


    val searchText = remember {
        viewModel.viewStates?.searchText
    }

    val selectBtnEnabled = remember {
        viewModel.viewStates?.selectBtnEnabled
    }

    val onSaveClicked = remember {
        {
            viewModel.setEvent(MapEvent.SavePickedLocation)
        }
    }

    val setCurrentLocationClicked = remember {
        {
            viewModel.setEvent(MapEvent.MoveToCurrentLocation)
        }
    }

    val backClicked = remember {
        {
            viewModel.setEvent(MapEvent.CloseScreenClicked)
        }
    }
    val updateCameraPosition: (CameraPosition) -> Unit = remember {
        {
            viewModel.viewStates?.cameraPosition?.value = it
            // viewModel.viewStates?.saveBtnEnabled?.value = true

        }
    }

    val moveCameraListener: () -> Unit = remember {
        {
            //  viewModel.viewStates?.selectBtnEnabled?.value = false
        }
    }


    val context = LocalContext.current

    val onResult: (ActivityResult) -> Unit = remember {
        {
            if (it.resultCode == RESULT_OK) {
                it.data?.let { data ->
                    val place = Autocomplete.getPlaceFromIntent(data)
                    place.latLng?.let { latLng ->

                        viewModel.setEvent(
                            MapEvent.UpdateLocation(
                                LatLng(
                                    latLng.latitude,
                                    latLng.longitude
                                )
                            )
                        )
                    }
                    place.name?.let { name ->
                        viewModel.viewStates?.searchText?.value = name
                    }

                }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult(), onResult
    )

    val onSearchIconClicked: () -> Unit = remember {
        {
            launcher.startGooglePlacesActivity(context)
        }
    }


    MapScreenContent(
        currentLocation = { currentLocation },
        fromHome = fromHome,
        searchText = { searchText ?: mutableStateOf("") },
        onSaveIconClicked = onSaveClicked,
        onSearchClicked = onSearchIconClicked,
        currentLocationClicked = setCurrentLocationClicked,
        backClicked = backClicked,
        updateCameraPositionClicked = updateCameraPosition,
        saveSelectEnabled = { selectBtnEnabled },
        moveCamera = moveCameraListener
    )


    GeneralObservers<MapState, MapViewModel>(viewModel = viewModel) {
        when (it) {
            is MapState.SendLocationToPreviousScreen -> {
                when (mode) {
                    MapScreenMode.EditAddress -> {
                        resultNavigator.navigateBack(result = it.addressInfo)
                    }
                    else -> {
                        if (fromAddAddress)
                            resultNavigator.navigateBack(result = it.addressInfo)
                        else {
                            navigator?.navigate(
                                direction = if (fromHome) AddAddressScreenMainDestination(
                                    addressInfo = it.addressInfo,
                                    fromHome = fromHome
                                ) else
                                    AddAddressScreenAuthDestination(
                                        addressInfo = it.addressInfo,
                                        fromHome = fromHome
                                    ),
                                false,
                            ) {

                                previousScreenRoute?.let{
                                    this.popUpTo(previousScreenRoute)
                                }
                            }
                        }

                    }
                }

            }
            MapState.FinishScreen -> {
                navigator?.popBackStack()
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }
}
