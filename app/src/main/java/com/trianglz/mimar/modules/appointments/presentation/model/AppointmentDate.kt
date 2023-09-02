package com.trianglz.mimar.modules.appointments.presentation.model

import com.trianglz.core.domain.enum.SimpleDateFormatEnum
import java.util.*

data class AppointmentDate(val day: String?, val month: String?, val year: String?) {
    companion object {
        fun String.toAppointmentDate(): AppointmentDate {
            val calendar = Calendar.getInstance()
            calendar.also {
                it.time = SimpleDateFormatEnum.DAY_MONTH_YEAR4.simpleDateFormat.parse(this) ?: Date()
            }
            val day = calendar.get(Calendar.DAY_OF_MONTH).toString()
            val month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
            val year = calendar.get(Calendar.YEAR).toString()
            return AppointmentDate(day, month, year)
        }
    }
}