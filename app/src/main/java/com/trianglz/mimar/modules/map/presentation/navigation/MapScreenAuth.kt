package com.trianglz.mimar.modules.map.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.libraries.maps.model.LatLng
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.trianglz.mimar.common.presentation.navigation.AuthGraph
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.map.presentation.MapScreen
import com.trianglz.mimar.modules.map.presentation.MapViewModel
import com.trianglz.mimar.modules.map.presentation.model.MapScreenMode

@AuthGraph
@Destination
@Composable
fun MapScreenAuth(navigator: DestinationsNavigator? = null,
                  resultNavigator: ResultBackNavigator<CustomerAddressUIModel>,
                  previousScreenRoute: String?=null,
                  latLng: LatLng? = null,
                  mode: MapScreenMode = MapScreenMode.AddAddress,
                  fromAddAddress: Boolean = false,
                  viewModel: MapViewModel = hiltViewModel(),
) {
    MapScreen(navigator, resultNavigator, previousScreenRoute,latLng, mode, fromAddAddress, false, viewModel)
}