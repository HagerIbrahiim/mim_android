package com.trianglz.mimar.modules.calendar.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.kizitonwose.calendar.core.yearMonth
import com.trianglz.core.domain.extensions.toIsoFormat
import com.trianglz.mimar.modules.cart.presentation.model.BaseCartUIModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

data class CalendarUIModel(
    val currentDate: LocalDate = LocalDate.now(),
    val currentMonth: YearMonth = currentDate.yearMonth,
    val startMonth: YearMonth = currentMonth,
    val endMonth: YearMonth = currentMonth.plusMonths(12),
    val openDays: List<DayOfWeek>? = null,
    val selectedDay: MutableState<LocalDate?> = mutableStateOf(null),
    val onDaySelected: (LocalDate) -> Unit = {
        if (selectedDay.value?.toIsoFormat() != it.toIsoFormat()) {
            selectedDay.value = it
        }
                                             },
    val targetDate: MutableState<LocalDate?> = mutableStateOf(null),
    val onTitleClicked: ((LocalDate) -> Unit)? = null,
)
