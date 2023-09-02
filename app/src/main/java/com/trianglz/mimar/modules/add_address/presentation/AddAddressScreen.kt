package com.trianglz.mimar.modules.add_address.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.libraries.maps.model.LatLng
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.OpenResultRecipient
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.trianglz.core.presentation.extensions.toActivityAsNewTaskWithParams
import com.trianglz.core.presentation.helper.showMaterialDialog
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.add_address.presentation.composables.AddAddressScreenContent
import com.trianglz.mimar.modules.add_address.presentation.contract.AddAddressEvent
import com.trianglz.mimar.modules.add_address.presentation.contract.AddAddressState
import com.trianglz.mimar.modules.add_address.presentation.contract.AddAddressViewState
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.destinations.*
import com.trianglz.mimar.modules.home.presentation.HomeActivity
import com.trianglz.mimar.modules.map.presentation.model.MapScreenMode

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddAddressScreen(
    viewModel: AddAddressViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null,
    mapScreenResult: OpenResultRecipient<CustomerAddressUIModel>,
    resultNavigator: ResultBackNavigator<CustomerAddressUIModel?>,

) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    //region Status Bar
    val systemUiController = rememberSystemUiController()
    val onDisposeDarkIcons = !isSystemInDarkTheme()

    val statusBarColor = remember(isSystemInDarkTheme()) {
        if (onDisposeDarkIcons) {
            Color.Transparent
        } else
            Color.White
    }


    DisposableEffect(systemUiController) {

        onDispose {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = onDisposeDarkIcons
            )
        }
    }

    SideEffect {
        systemUiController.setStatusBarColor(
                color = Color.White,
                darkIcons =  onDisposeDarkIcons
            )
    }
    //end region

    val viewStates = remember {
        viewModel.viewStates ?: AddAddressViewState()
    }
    val currentLocation = remember {
        viewStates.location
    }

    val addressTitle by remember {
        viewStates.addressTitle
    }

    val city by remember {
        viewStates.city
    }

    val country by remember {
        viewStates.countryName
    }

    val streetName by remember {
        viewStates.streetName
    }

    val buildingNum by remember {
        viewStates.buildingNum
    }

    val district by remember {
        viewStates.district
    }

    val secondaryNum by remember {
        viewStates.secondaryNum
    }

    val landmarkNotes by remember {
        viewStates.landmarkNotes
    }

    val mode by remember {
        viewStates.mode
    }

    val fromHome by remember {
        viewStates.fromHome
    }


    val editAddressClicked = remember {
        {
            viewModel.setEvent(AddAddressEvent.EditMapClicked)
        }
    }

    val saveAddressClicked = remember {
        {
            viewModel.setEvent(AddAddressEvent.SaveButtonClicked)
        }
    }

    val onBackButtonClicked = remember {
        {
            viewModel.setEvent(AddAddressEvent.BackButtonClicked)
        }
    }





    val isButtonValid by remember(
        addressTitle.textFieldValue.value,
        streetName.textFieldValue.value,
        buildingNum.textFieldValue.value,
        district.textFieldValue.value,
        country.textFieldValue.value,
        secondaryNum.textFieldValue.value
    ) {
        derivedStateOf {
            addressTitle.isValid.value
                    && streetName.isValid.value
                    && buildingNum.isValid.value
                    && district.isValid.value
                    && country.isValid.value
                    && secondaryNum.isValid.value
        }
    }

    mapScreenResult.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }
            is NavResult.Value -> {
                viewModel.setEvent(AddAddressEvent.UpdateFetchedLocation(result.value))
            }
        }
    }

    AddAddressScreenContent(
        navigator = navigator,
        userModeHandler = viewModel.userModeHandler,
        { mode },
        { currentLocation.value },
        { addressTitle },
        { country },
        { city },
        { streetName },
        { buildingNum },
        { district },
        { secondaryNum },
        { landmarkNotes },
        { isButtonValid },
        { fromHome ?: false },
        editAddressClicked,
        saveAddressClicked,
        onBackButtonClicked,
        notificationsCount = viewModel.notificationCount,
        cartCount = viewModel.cartCount
    )

    GeneralObservers<AddAddressState, AddAddressViewModel>(viewModel = viewModel) {
        when (it) {
            AddAddressState.OpenMapScreen -> {
                keyboardController?.hide()
                navigator?.navigate(
                    if(fromHome == true)
                        MapScreenMainDestination(
                        fromAddAddress = true,
                            previousScreenRoute = AddressesListMainDestination.route,

                        mode = mode,
                        latLng = if (currentLocation.value != null)
                            currentLocation.value?.let { loc ->
                                LatLng(
                                    loc.latitude,
                                    loc.longitude
                                )
                            } else null)
                    else
                        MapScreenAuthDestination(
                            previousScreenRoute = AddressesListAuthDestination.route,
                            fromAddAddress = true,
                            mode = mode,
                            latLng = if (currentLocation.value != null)
                                currentLocation.value?.let { loc ->
                                    LatLng(
                                        loc.latitude,
                                        loc.longitude
                                    )
                                } else null)
                )

            }
            AddAddressState.FinishScreen -> {
                navigator?.popBackStack()
            }

            is AddAddressState.OpenAddressesScreen -> {
                resultNavigator.navigateBack(result = it.address)
            }
            AddAddressState.ShowCancelAddEditDialog -> {
                showMaterialDialog(
                    context,
                    context.getString(if(mode == MapScreenMode.AddAddress)
                        R.string.add_address else  R.string.edit_address),
                    context.getString(if(mode == MapScreenMode.AddAddress)
                        R.string.are_you_sure_cancel_add_address else  R.string.are_you_sure_cancel_edit_address),
                    context.getString(R.string.yes),
                    context.getString(R.string.no),
                    {
                        viewModel.setEvent(AddAddressEvent.CloseScreen)
                    },
                    null
                )
            }
            AddAddressState.OpenHome -> {
                context.toActivityAsNewTaskWithParams<HomeActivity>()
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }

}

