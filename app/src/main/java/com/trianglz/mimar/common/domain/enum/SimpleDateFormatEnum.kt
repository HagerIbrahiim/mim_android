package com.trianglz.mimar.common.domain.enum

import com.trianglz.core.domain.enum.SimpleDateFormatEnum
import java.text.SimpleDateFormat
import java.util.*

object SimpleDateFormatData {
    val DayEn = SimpleDateFormatEnum(SimpleDateFormat("E", Locale.ENGLISH))
    val Day = SimpleDateFormatEnum(SimpleDateFormat("E", Locale.getDefault()))
    val DayMonthYearEn = SimpleDateFormatEnum(SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH))
    val DayMonthYear = SimpleDateFormatEnum(SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()))
    val DayDate = SimpleDateFormatEnum(SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault()))
    val YearMonthDayEn = SimpleDateFormatEnum(SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH))
    val DayMonthYear2 = SimpleDateFormatEnum(SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()))
}