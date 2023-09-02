package com.trianglz.mimar.modules.appointment_details.data.remote.data_source

import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentDetailsDataModel
import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentProblemReasonsDataModel
import com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.request.UpdateAppointmentRequest
import com.trianglz.mimar.modules.appointment_details.data.remote.retrofit.service.AppointmentDetailsService
import javax.inject.Inject

class AppointmentDetailsRemoteDataSourceImpl @Inject constructor(private val service: AppointmentDetailsService) :
    AppointmentDetailsRemoteDataSource {
    override suspend fun getAppointmentDetails(appointmentId: Int): AppointmentDetailsDataModel {
        return service.getAppointmentDetailsAsync(appointmentId).appointment ?: AppointmentDetailsDataModel()
    }


    override suspend fun updateAppointment(
        appointmentId: Int,
        status: String?,
        paymentMethod: String?,
        appointmentProblemReasonId: Int?,
    ): AppointmentDetailsDataModel {
        return service.updateAppointment(
            appointmentId,
            UpdateAppointmentRequest(status,paymentMethod,  appointmentProblemReasonId),
        ).appointment ?: AppointmentDetailsDataModel()
    }
    override suspend fun getAppointmentReasonsList(): List<AppointmentProblemReasonsDataModel> {
        return service.getAppointmentProblemReasonsListAsync().appointmentProblemReasons ?: listOf()
    }

    override suspend fun getAppointmentCancellationPolicy(): String {
        return service.getAppointmentCancellationPolicyAsync().policy ?: ""
    }


}