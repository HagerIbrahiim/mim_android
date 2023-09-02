package com.trianglz.mimar.modules.appointments_list.presentation

import com.trianglz.mimar.common.presentation.base.MimarBaseViewModel
import com.trianglz.mimar.modules.appointments_list.domain.usecase.GetAppointmentStatusListUseCase
import com.trianglz.mimar.modules.appointments_list.presentation.contract.AppointmentsEvent
import com.trianglz.mimar.modules.appointments_list.presentation.contract.AppointmentsState
import com.trianglz.mimar.modules.appointments_list.presentation.contract.AppointmentsViewState
import com.trianglz.mimar.modules.appointments_list.presentation.mapper.toUI
import com.trianglz.mimar.modules.appointments_list.presentation.model.AppointmentStatusUIModel
import com.trianglz.mimar.modules.appointments_list.presentation.source.AppointmentsSource
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppointmentsViewModel @Inject constructor(
    val source: AppointmentsSource,
    private val getAppointmentStatusList: GetAppointmentStatusListUseCase,
    val userModeHandler: UserModeHandler,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
) : MimarBaseViewModel<AppointmentsEvent, AppointmentsViewState, AppointmentsState>(getUserUpdatesUseCase) {



    override fun handleEvents(event: AppointmentsEvent) {
        when (event) {
            is AppointmentsEvent.ChangeAppointmentStatus -> {
                handleSelectedAppointmentStatus(event.statusId)
            }
            AppointmentsEvent.RefreshAppointments -> {
                resetNetworkRefresh()
                getAppointmentsList()
            }
            AppointmentsEvent.RefreshScreen -> {
                viewStates?.isRefreshing?.value = true
                resetViewStates()
                getAppointmentStatusList()
            }

            is AppointmentsEvent.AppointmentItemClicked -> {
                setState { AppointmentsState.OpenAppointmentDetails(event.appointmentId) }
            }
        }
    }

    init {
        startListenForUserUpdates()
        source.onClick = {
            setEvent(AppointmentsEvent.AppointmentItemClicked(it))
        }
        getAppointmentStatusList()
    }

    private  fun getAppointmentStatusList() {

        launchCoroutine{
            setLoadingWithShimmer()
            val appointmentsList = getAppointmentStatusList.execute()?.map {
                it.toUI (onClick = {
                    setEvent(AppointmentsEvent.ChangeAppointmentStatus(it))
                })
            }

            if (appointmentsList?.isNotEmpty() == true) {
                val selectedAppointment: AppointmentStatusUIModel = if (viewStates?.selectedCategoryId == 0) {
                    appointmentsList[0]
                } else {
                    appointmentsList.find { it.uniqueId == viewStates?.selectedCategoryId } ?: appointmentsList[0]
                }
                selectedAppointment.isChecked.value = true
                viewStates?.appointmentsStatusList?.clear()
                viewStates?.appointmentsStatusList?.addAll(appointmentsList)
                viewStates?.selectedAppointment?.value = selectedAppointment
            }
            else {
                viewStates?.appointmentsStatusList?.clear()
            }
            setDoneLoading()
            getAppointmentsList()
        }
    }

    private fun handleSelectedAppointmentStatus(selectedStatusId: Int) {
        if (selectedStatusId != viewStates?.selectedAppointment?.value?.uniqueId) {
            val appointmentsStatusList = viewStates?.appointmentsStatusList
            viewStates?.selectedCategoryId = selectedStatusId

            val oldSelectedDateIndex = appointmentsStatusList?.indexOfFirst { it.isChecked.value }
            val newSelectedDateIndex = appointmentsStatusList?.indexOfFirst { it.uniqueId == selectedStatusId }

            oldSelectedDateIndex?.let {
                appointmentsStatusList[oldSelectedDateIndex].isChecked.value = false
            }
            newSelectedDateIndex?.let {
                val updatedSpecialty = appointmentsStatusList[newSelectedDateIndex]
                appointmentsStatusList[newSelectedDateIndex].isChecked.value = true
                viewStates?.selectedAppointment?.value = updatedSpecialty
            }
            getAppointmentsList()

        }
    }
    override fun resetViewStates() {
        resetNetworkRefresh()
        viewStates?.appointmentsStatusList?.clear()
        viewStates?.appointmentsStatusList?.addAll(AppointmentStatusUIModel.getShimmerList())
    }

    private fun resetNetworkRefresh(){
        viewStates?.isRefreshing?.value = false
        viewStates?.networkError?.value = false
    }
    private fun getAppointmentsList(){
        source.status = viewStates?.selectedAppointment?.value?.statusType?.key
        source.refreshAll()
    }

    override fun createInitialViewState(): AppointmentsViewState {
        return AppointmentsViewState()
    }

}
