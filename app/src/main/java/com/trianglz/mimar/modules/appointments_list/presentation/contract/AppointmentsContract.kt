package com.trianglz.mimar.modules.appointments_list.presentation.contract


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.appointments_list.presentation.model.AppointmentStatusUIModel
import kotlinx.coroutines.flow.MutableStateFlow


/// Events that user performed
sealed class AppointmentsEvent : BaseEvent {
    object RefreshScreen: AppointmentsEvent()
    object RefreshAppointments: AppointmentsEvent()
    data class ChangeAppointmentStatus(val statusId: Int): AppointmentsEvent()
    data class AppointmentItemClicked(val appointmentId: Int): AppointmentsEvent()

}

sealed class AppointmentsState : BaseState {
    data class OpenAppointmentDetails(val appointmentId: Int) : AppointmentsState()

}

data class AppointmentsViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var appointmentsStatusList: SnapshotStateList<AppointmentStatusUIModel>? = mutableStateListOf(),
    val selectedAppointment: MutableState<AppointmentStatusUIModel?> = mutableStateOf(null),
    var selectedCategoryId: Int = 0,

    ) : BaseViewState