package com.trianglz.mimar.modules.add_service.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.add_service.presentation.composables.AddServiceScreenContent
import com.trianglz.mimar.modules.add_service.presentation.contract.AddServiceEvent
import com.trianglz.mimar.modules.add_service.presentation.contract.AddServiceState
import com.trianglz.mimar.modules.add_service.presentation.model.AddServiceNavArgs

@Composable
@MainGraph
@Destination(navArgsDelegate = AddServiceNavArgs::class)
fun AddServiceScreen(
    viewModel: AddServiceViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null
) {

    val source = remember {
        viewModel.source
    }

    val onCartClicked = remember {
        {
            viewModel.setEvent(AddServiceEvent.CartClicked)
        }
    }


    AddServiceScreenContent(
        navigator = navigator,
        userModeHandler = viewModel.userModeHandler,
        source = { source },
        onCartClicked = onCartClicked,
        notificationsCount = viewModel.notificationCount,
        cartCount = viewModel.cartCount
    )

    GeneralObservers<AddServiceState, AddServiceViewModel>(viewModel = viewModel) {
        when (it) {
            AddServiceState.CloseScreen -> {
                navigator?.popBackStack()
            }

            AddServiceState.OpenCartScreen -> {
                navigator?.popBackStack()
            }

        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {}

}

