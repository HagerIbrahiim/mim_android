package com.trianglz.mimar.modules.appointments_list.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents.Companion.LocalMainComponent
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.calculateBottomPadding
import com.trianglz.core_compose.presentation.pagination.source.ComposePaginatedListDataSource
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.compose_views.MimarRoundedHeader
import com.trianglz.mimar.common.presentation.selectables.SelectableList
import com.trianglz.mimar.common.presentation.ui.theme.bottomNavigationHeight
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentUIModel
import com.trianglz.mimar.modules.appointments_list.presentation.model.AppointmentStatusUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler

@Composable
fun AppointmentsScreenContent(
    userModeHandler: UserModeHandler,
    navigator: DestinationsNavigator,
    isRefreshing: @Composable () -> Boolean,
    showNetworkError: @Composable () -> Boolean,
    source: () -> ComposePaginatedListDataSource<AppointmentUIModel>,
    appointments: () -> List<AppointmentStatusUIModel>,
    selectedAppointmentStatus: () -> AppointmentStatusUIModel?,
    refreshAppointments: () -> Unit,
    refreshAllScreen: () -> Unit,
    onAppointmentStatusClicked: (Int) -> Unit,
    notificationsCount: State<Int>,
    cartCount: State<Int>,
) {



    MimarRoundedHeader(
        navigator = navigator,
        screenTitle = R.string.my_appointments,
        addElevation = false,
        userModeHandler = userModeHandler,
        welcomeText = null,
        notificationsCount = notificationsCount,
        cartCount = cartCount
    ) {
        Column {
            if (showNetworkError()) {
                LocalMainComponent.NetworkError(modifier = Modifier, addPadding = false) {
                    refreshAllScreen()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .calculateBottomPadding(MaterialTheme.dimens.bottomNavigationHeight),

                    ) {
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.spaceBetweenItemsXLarge))

                    SelectableList(showLoading = isRefreshing,
                        { appointments() }, { selectedAppointmentStatus()?.uniqueId ?: -1 },
                    onItemClicked = onAppointmentStatusClicked)


                    AppointmentsPaginatedList(
                        source,
                        selectedAppointmentText = { selectedAppointmentStatus()?.statusType?.name },
                        onRefresh = refreshAppointments,
                    )

                }
            }

        }
    }
}
