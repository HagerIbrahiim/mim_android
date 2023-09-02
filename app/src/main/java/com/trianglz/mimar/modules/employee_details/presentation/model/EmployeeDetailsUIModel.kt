package com.trianglz.mimar.modules.employee_details.presentation.model

import com.trianglz.mimar.common.domain.model.CoveredZonesDomainModel
import com.trianglz.mimar.common.presentation.models.WorkingHoursUIModel
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.offered_location.domain.model.toList

data class EmployeeDetailsUIModel(
    override val rating: Double? = null,
    val coveredZones: List<CoveredZonesDomainModel>? = null,
    val employeeNumber: String? = null,
    val employeeWorkingHours: List<WorkingHoursUIModel>? = null,
    val firstName: String? = null,
    override val id: Int? = null,
    override val image: String? = null,
    override val isDeleted: Boolean? = null,
    val lastName: String? = null,
    override val nextAvailableSlot: String? = null,
    override val offeredLocation: OfferedLocationType? = null,
    override val phoneNumber: String? = null,
    val services: List<EmployeeSpecialtyUIModel>? = null,
    override val userName: String="",
    override val showShimmer: Boolean = false,
    ) : EmployeeUIModel(id) {

    val offeredLocationList = offeredLocation?.toList()


    companion object {
        fun getShimmer() = EmployeeDetailsUIModel(
            id = -1,
            showShimmer = true,
        )
    }
}