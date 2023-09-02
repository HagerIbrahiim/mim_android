package com.trianglz.mimar.modules.ratings.presenation.model

import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel

data class ReviewAppointmentSheetData(
    val appointmentId: Int? = null,
    val branchName: String? = null,
    val employeesList: List<EmployeeUIModel>? = listOf(),
)