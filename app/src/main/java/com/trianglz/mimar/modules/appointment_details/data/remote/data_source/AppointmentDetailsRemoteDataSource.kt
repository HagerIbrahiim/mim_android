package com.trianglz.mimar.modules.appointment_details.data.remote.data_source

import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentDetailsDataModel
import com.trianglz.mimar.modules.appointment_details.data.remote.model.AppointmentProblemReasonsDataModel


interface AppointmentDetailsRemoteDataSource {
    suspend fun getAppointmentDetails(appointmentId: Int): AppointmentDetailsDataModel

    suspend fun updateAppointment(
        appointmentId: Int,
        status: String? = null,
        paymentMethod: String? = null,
        appointmentProblemReasonId: Int?=null,
    ): AppointmentDetailsDataModel

    suspend fun getAppointmentReasonsList() : List<AppointmentProblemReasonsDataModel>
    suspend fun getAppointmentCancellationPolicy() : String

}