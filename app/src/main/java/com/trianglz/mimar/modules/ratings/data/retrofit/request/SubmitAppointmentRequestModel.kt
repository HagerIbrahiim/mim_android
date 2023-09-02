package com.trianglz.mimar.modules.ratings.data.retrofit.request

import com.squareup.moshi.Json
import com.trianglz.mimar.modules.ratings.data.model.EmployeeRatingDataModel
import com.trianglz.mimar.modules.ratings.domain.model.EmployeeRatingDomainModel

data class SubmitAppointmentRequestModel (
    @Json(name = "rating")
    val rating: Int?= null,
    @Json(name = "feedback")
    val feedback: String?= null,
    @Json(name = "appointment_id")
    val appointmentId: Int?=null,
    @Json(name = "employee_ratings_attributes")
    val employeeRatingsAttributes: List<EmployeeRatingDataModel>?=null
)