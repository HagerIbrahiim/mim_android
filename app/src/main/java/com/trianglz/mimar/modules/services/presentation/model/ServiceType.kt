package com.trianglz.mimar.modules.services.presentation.model

sealed class ServiceType {
    object ServiceWithAddButton: ServiceType()
    object EmployeeService: ServiceType()
    object SearchService: ServiceType()
    object CartService: ServiceType()
    object AppointmentService: ServiceType()
}