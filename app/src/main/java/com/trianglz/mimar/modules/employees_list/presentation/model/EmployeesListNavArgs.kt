package com.trianglz.mimar.modules.employees_list.presentation.model


data class EmployeesListNavArgs(
    val selectedEmployeeId: Int?=null,
    val cartBranchServiceId: Int? = null,
    val date: String?= null,
    val offeredLocation: String?= null,
)
