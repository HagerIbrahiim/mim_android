package com.trianglz.mimar.modules.notification.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.core_compose.presentation.helper.LocalNavController
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsScreenMode
import com.trianglz.mimar.modules.destinations.AppointmentDetailsScreenDestination
import com.trianglz.mimar.modules.destinations.CartScreenDestination
import com.trianglz.mimar.modules.notification.presentation.composables.NotificationsScreenContent
import com.trianglz.mimar.modules.notification.presentation.contract.NotificationsListState

@MainGraph
@Destination
@Composable
fun NotificationsListScreen(
    viewModel: NotificationsListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator? = null,
) {

    //region Status Bar
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )

        onDispose {}
    }
    //endregion


    NotificationsScreenContent(
        source = { viewModel.source },
        navigator = navigator,
        userModeHandler = viewModel.userModeHandler,
        cartCount = viewModel.cartCount,
    )

    val previousScreenDestination = LocalNavController.current.previousBackStackEntry?.destination

    GeneralObservers<NotificationsListState, NotificationsListViewModel>(viewModel = viewModel) {
        when (it) {
            is NotificationsListState.OpenAppointmentDetails -> {
                navigator?.navigate(AppointmentDetailsScreenDestination(appointmentId = it.id,
                mode = AppointmentDetailsScreenMode.AppointmentDetails,
                openReviewAppointment = it.openReviewBottomSheet ?: false))
            }
            NotificationsListState.OpenCart -> {
                if(previousScreenDestination?.route == CartScreenDestination.route) {
                    navigator?.popBackStack()
                }
                else {
                    navigator?.navigate(CartScreenDestination)
                }
            }
        }

    }

    HandleLoadingStateObserver(viewModel = viewModel) {
    }

}
