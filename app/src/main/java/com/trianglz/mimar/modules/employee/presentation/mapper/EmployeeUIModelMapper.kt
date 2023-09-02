package com.trianglz.mimar.modules.employee.presentation.mapper

import com.trianglz.mimar.modules.employee.domain.model.EmployeeDomainModel
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import com.trianglz.mimar.modules.ratings.presenation.model.RatingAppointmentUIModel


fun EmployeeDomainModel.toUI(onClick: (id: Int) -> Unit) = EmployeeUIModel(
    id, userName, image, phoneNumber, offeredLocation, rating, nextAvailableSlot,isDeleted,
    isAvailable = nextAvailableSlot != null,
    isAnyone = isAnyone,
    onClick = onClick
)

fun EmployeeUIModel.toDomain() = EmployeeDomainModel(
    id, userName, image, phoneNumber, offeredLocation, rating, nextAvailableSlot,  isDeleted, isAnyone
)

fun EmployeeUIModel.toEmployeeRatingUIModel(onValueChange: (Float) -> Unit) = RatingAppointmentUIModel(
    this.id ?: -1,this.userName, onValueChange = onValueChange
)