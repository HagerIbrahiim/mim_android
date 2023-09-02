package com.trianglz.mimar.modules.addresses_list.presentation.navigation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.OpenResultRecipient
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.addresses_list.presentation.AddressesListScreen
import com.trianglz.mimar.modules.destinations.AddAddressScreenAuthDestination
import com.trianglz.mimar.modules.destinations.AddAddressScreenMainDestination

@Destination
@MainGraph
@Composable
fun AddressesListMain(
    navigator: DestinationsNavigator? = null,
    addAddressScreenResult: ResultRecipient<AddAddressScreenMainDestination, CustomerAddressUIModel?>,
) {

    AddressesListScreen(
        navigator = navigator, addAddressScreenResult = addAddressScreenResult,
        fromHome = true
    )
}