package com.trianglz.mimar.modules.appointment_details.domain.repository

import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentDetailsDomainModel
import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentProblemReasonsDomainModel

interface AppointmentDetailsRepository {
    suspend fun getAppointmentDetails(appointmentId: Int): AppointmentDetailsDomainModel

    suspend fun updateAppointment(
        appointmentId: Int,
        status: String? = null,
        paymentMethod: String? = null,
        appointmentProblemReasonId: Int?=null,
    ): AppointmentDetailsDomainModel

    suspend fun getAppointmentReasonsList() : List<AppointmentProblemReasonsDomainModel>
    suspend fun getAppointmentCancellationPolicy() : String


}