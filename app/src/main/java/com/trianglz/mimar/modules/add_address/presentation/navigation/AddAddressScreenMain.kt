package com.trianglz.mimar.modules.add_address.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.add_address.presentation.AddAddressScreen
import com.trianglz.mimar.modules.add_address.presentation.AddAddressViewModel
import com.trianglz.mimar.modules.add_address.presentation.model.AddAddressNavArgs
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.destinations.MapScreenMainDestination
import com.trianglz.mimar.modules.map.presentation.model.MapScreenMode

@MainGraph
@Destination(navArgsDelegate = AddAddressNavArgs::class)
@Composable
fun AddAddressScreenMain(
    viewModel: AddAddressViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null,
    mapScreenResult: ResultRecipient<MapScreenMainDestination, CustomerAddressUIModel>,
    resultNavigator: ResultBackNavigator<CustomerAddressUIModel?>,
) {
    AddAddressScreen(viewModel, navigator, mapScreenResult, resultNavigator, )
}