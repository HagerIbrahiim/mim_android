package com.trianglz.mimar.modules.appointments_list.presentation

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core.presentation.extensions.toast
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsScreenMode
import com.trianglz.mimar.modules.appointments_list.presentation.composables.AppointmentsScreenContent
import com.trianglz.mimar.modules.appointments_list.presentation.contract.AppointmentsEvent
import com.trianglz.mimar.modules.appointments_list.presentation.contract.AppointmentsState
import com.trianglz.mimar.modules.appointments_list.presentation.contract.AppointmentsViewState
import com.trianglz.mimar.modules.destinations.AppointmentDetailsScreenDestination

@MainGraph(start = false)
@Destination
@Composable
fun AppointmentsScreen(
    navigator: DestinationsNavigator,
    viewModel: AppointmentsViewModel = hiltViewModel(),
) {

    //region Status Bar
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
        )

        onDispose {}
    }
    //endregion

    val viewStates = remember {
        viewModel.viewStates ?: AppointmentsViewState()
    }

    val showNetworkError: @Composable (() -> Boolean) = remember(viewStates.networkError) {
        { viewStates.networkError.collectAsState().value }
    }

    val isRefreshing: @Composable (() -> Boolean) = remember(viewStates.isRefreshing) {
        { viewStates.isRefreshing.collectAsState().value }
    }

    val appointmentsStatusList = remember {
        viewStates.appointmentsStatusList ?: listOf()
    }

    val selectedAppointment by remember {
        viewStates.selectedAppointment
    }


    val refreshAppointments: () -> Unit = remember {
        {
            viewModel.setEvent(AppointmentsEvent.RefreshAppointments)
        }
    }

    val refreshScreen: () -> Unit = remember {
        {
            viewModel.setEvent(AppointmentsEvent.RefreshScreen)
        }
    }

    val onAppointmentItemClicked: (Int) -> Unit = remember {
        {
            viewModel.setEvent(AppointmentsEvent.ChangeAppointmentStatus(it))
        }
    }



    AppointmentsScreenContent(
        viewModel.userModeHandler,
        navigator,
        isRefreshing,
        showNetworkError,
        { viewModel.source },
        { appointmentsStatusList },
        { selectedAppointment },
        refreshAppointments,
        refreshScreen,
        onAppointmentItemClicked,
        notificationsCount = viewModel.notificationCount,
        cartCount = viewModel.cartCount,
    )



    GeneralObservers<AppointmentsState, AppointmentsViewModel>(viewModel = viewModel) {
        when (it) {
            is AppointmentsState.OpenAppointmentDetails -> {
                navigator.navigate(
                    AppointmentDetailsScreenDestination(
                        it.appointmentId, mode = AppointmentDetailsScreenMode.AppointmentDetails
                    ),
                    builder = {
                        launchSingleTop = true
                    }
                )
            }
        }
    }
}