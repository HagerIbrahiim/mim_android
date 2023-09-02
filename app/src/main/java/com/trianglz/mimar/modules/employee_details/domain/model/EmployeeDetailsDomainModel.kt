package com.trianglz.mimar.modules.employee_details.domain.model


import com.trianglz.mimar.common.domain.model.CoveredZonesDomainModel
import com.trianglz.mimar.common.domain.model.WorkingHoursDomainModel
import com.trianglz.mimar.modules.employee.domain.model.EmployeeDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType

data class EmployeeDetailsDomainModel(
    override val rating: Double? = null,
    val coveredZones: List<CoveredZonesDomainModel>? = null,
    val employeeNumber: String? = null,
    val employeeWorkingHours: List<WorkingHoursDomainModel>? = null,
    val firstName: String? = null,
    override val id: Int? = null,
    override val image: String? = null,
    override val isDeleted: Boolean? = null,
    val lastName: String? = null,
    override val nextAvailableSlot: String? = null,
    override val offeredLocation: OfferedLocationType? = null,
    override val phoneNumber: String? = null,
    val services: List<EmployeeSpecialtyDomainModel>? = null,
    override val userName: String
) : EmployeeDomainModel(
    id, userName, image, phoneNumber, offeredLocation,
    rating ?: 0.0, nextAvailableSlot, isDeleted
)