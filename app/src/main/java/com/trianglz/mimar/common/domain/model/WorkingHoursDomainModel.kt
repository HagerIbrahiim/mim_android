package com.trianglz.mimar.common.domain.model



data class WorkingHoursDomainModel (
    val id: Int?,
    val weekDay: String,
    val intervals: List<WorkingHoursIntervalsDomainModel>?
)


