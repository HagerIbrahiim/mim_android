package com.trianglz.mimar.modules.ratings.data.mapper

import com.trianglz.mimar.modules.ratings.data.retrofit.request.SubmitAppointmentRequestModel
import com.trianglz.mimar.modules.ratings.domain.model.SubmitAppointmentReviewDomainModel

fun SubmitAppointmentReviewDomainModel.toRequestModel() = SubmitAppointmentRequestModel(
    rating,feedback,appointmentId, employeeRatingsAttributes?.map { it.toData() }
)