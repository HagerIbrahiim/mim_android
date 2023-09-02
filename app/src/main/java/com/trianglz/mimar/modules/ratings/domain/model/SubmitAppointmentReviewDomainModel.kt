package com.trianglz.mimar.modules.ratings.domain.model


data class SubmitAppointmentReviewDomainModel (
    val rating: Int?= null,
    val feedback: String?= null,
    val appointmentId: Int?=null,
    val employeeRatingsAttributes: List<EmployeeRatingDomainModel>?=null
)