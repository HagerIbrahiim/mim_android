package com.trianglz.mimar.common.presentation.models

import com.trianglz.core.domain.enum.SimpleDateFormatEnum
import com.trianglz.core.domain.extensions.formatDate
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.utils.getAppLocale
import com.trianglz.mimar.common.domain.enum.SimpleDateFormatData.Day
import com.trianglz.mimar.common.domain.enum.SimpleDateFormatData.DayEn
import com.trianglz.mimar.R

data class WorkingHoursUIModel(
    val id: Int?,
    val weekDay: String,
    val intervals: List<WorkingHoursIntervalsUIModel>?
) {
    /**
     * Sunday - الأحد
     */
    val formattedWeekDay =
        if (getAppLocale() == Locales.ARABIC.code) {
            weekDay.uppercase().formatDate(
                DayEn, Day,
            )
        } else weekDay.capitalize()

    /**
     * returns 08:00AM - 05:00 PM or Day off
     */
    fun getIntervalText(index: Int = 0, offDayText: Int =  R.string.closed): StringWrapper {
        return if (intervals.isNullOrEmpty())
            return StringWrapper(offDayText)

        else {
            StringWrapper(
                "${
                    intervals[index].startsAt.formatDate(
                        SimpleDateFormatEnum.HourMinutes24FormatEng,
                        SimpleDateFormatEnum.HourMinutes12Format
                    )
                } - ${
                    intervals[index].endsAt.formatDate(
                        SimpleDateFormatEnum.HourMinutes24FormatEng,
                        SimpleDateFormatEnum.HourMinutes12Format
                    )
                }"
            )
        }
    }
}

