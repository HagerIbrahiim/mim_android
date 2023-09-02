package com.trianglz.mimar.modules.calendar.presentation.mapper

import java.time.DayOfWeek

fun String.toDayOfWeekModel(): DayOfWeek {
    return when(this.lowercase()) {
        DayOfWeek.SATURDAY.name.lowercase() -> DayOfWeek.SATURDAY
        DayOfWeek.SUNDAY.name.lowercase() -> DayOfWeek.SUNDAY
        DayOfWeek.MONDAY.name.lowercase() -> DayOfWeek.MONDAY
        DayOfWeek.TUESDAY.name.lowercase() -> DayOfWeek.TUESDAY
        DayOfWeek.WEDNESDAY.name.lowercase() -> DayOfWeek.WEDNESDAY
        DayOfWeek.THURSDAY.name.lowercase() -> DayOfWeek.THURSDAY
        DayOfWeek.FRIDAY.name.lowercase() -> DayOfWeek.FRIDAY
        else -> DayOfWeek.FRIDAY
    }
}

fun List<DayOfWeek>.getOpenWeekDays(): List<DayOfWeek>{
    val allDays = listOf(
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY
    )
    val openDays = allDays.subtract(this.toSet())
    return openDays.toList()
}