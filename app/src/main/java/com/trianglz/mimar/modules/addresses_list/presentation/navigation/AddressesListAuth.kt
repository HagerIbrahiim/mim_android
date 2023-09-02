package com.trianglz.mimar.modules.addresses_list.presentation.navigation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.trianglz.mimar.common.presentation.navigation.AuthGraph
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.addresses_list.presentation.AddressesListScreen
import com.trianglz.mimar.modules.destinations.AddAddressScreenAuthDestination

@Destination
@AuthGraph
@Composable
fun AddressesListAuth(
    navigator: DestinationsNavigator? = null,
    addAddressScreenResult: ResultRecipient<AddAddressScreenAuthDestination, CustomerAddressUIModel?>,
) {

    AddressesListScreen(
        navigator = navigator, addAddressScreenResult = addAddressScreenResult,
        fromHome = false
    )
}