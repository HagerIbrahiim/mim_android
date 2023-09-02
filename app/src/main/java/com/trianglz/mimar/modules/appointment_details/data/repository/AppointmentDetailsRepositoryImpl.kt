package com.trianglz.mimar.modules.appointment_details.data.repository

import com.trianglz.mimar.modules.appointment_details.data.remote.data_source.AppointmentDetailsRemoteDataSource
import com.trianglz.mimar.modules.appointment_details.data.remote.mapper.toDomain
import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentDetailsDomainModel
import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentProblemReasonsDomainModel
import com.trianglz.mimar.modules.appointment_details.domain.repository.AppointmentDetailsRepository
import javax.inject.Inject

class AppointmentDetailsRepositoryImpl @Inject constructor(
    private val remote: AppointmentDetailsRemoteDataSource, ) :
    AppointmentDetailsRepository {
    override suspend fun getAppointmentDetails(appointmentId: Int): AppointmentDetailsDomainModel {
       return remote.getAppointmentDetails(appointmentId).toDomain()
    }

    override suspend fun updateAppointment(
        appointmentId: Int,
        status: String?,
        paymentMethod: String?,
        appointmentProblemReasonId: Int?,
    ): AppointmentDetailsDomainModel {
        return remote.updateAppointment(appointmentId, status, paymentMethod, appointmentProblemReasonId).toDomain()
    }

    override suspend fun getAppointmentReasonsList(): List<AppointmentProblemReasonsDomainModel> {
        return remote.getAppointmentReasonsList().map { it.toDomain() }
    }

    override suspend fun getAppointmentCancellationPolicy(): String {
        return remote.getAppointmentCancellationPolicy()
    }


}