package com.trianglz.mimar.modules.calendar.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.mimar.common.presentation.extensions.ifTrue
import com.trianglz.mimar.common.presentation.ui.theme.selectedItemBackground
import com.trianglz.mimar.common.presentation.ui.theme.xSmall
import com.trianglz.mimar.modules.calendar.presentation.model.CalendarUIModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun WeekViewCalendar(
    calendarUIModel: () -> CalendarUIModel,
) {
    val item = remember {
        calendarUIModel()
    }
    val daysOfWeek = remember { daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY) }

    Column(
        modifier = Modifier
            .fillMaxWidth().wrapContentHeight(),
    ) {

        val weekState = rememberWeekCalendarState(
            startDate = item.startMonth.atStartOfMonth(),
            endDate = item.endMonth.atEndOfMonth(),
            firstVisibleWeekDate = item.currentDate,
            firstDayOfWeek = DayOfWeek.MONDAY,
        )
        LaunchedEffect(key1 = item.targetDate) {
            snapshotFlow {
                item.targetDate.value
            }.filterNotNull().distinctUntilChanged().collectLatest {
                weekState.animateScrollToWeek(it)
            }
        }
        WeekCalendarTitle(weekState = weekState, item.onTitleClicked ?: {})
        CalendarHeader(daysOfWeek = daysOfWeek)

        WeekCalendar(
            state = weekState,
            dayContent = { day ->
                DayContent(day = day) {
                    item
                }
            },
        )
    }
}

@Composable
fun DayContent(
    day: WeekDay,
    calendarUIModel: () -> CalendarUIModel
) {
    val item = remember {
        calendarUIModel()
    }
    val selectedDay by remember { item.selectedDay }

    val isSelectable = remember {
            day.date >= item.currentDate && item.openDays?.contains(day.date.dayOfWeek) ?: true
    }
    val isCurrentDay = remember {
        day.date == item.currentDate
    }
    val isSelected = remember(selectedDay) {
            selectedDay == day.date
    }
    Day(
        day.date,
        isSelected = isSelected,
        isCurrentDay = isCurrentDay,
        isSelectable = isSelectable,
        onClick = item.onDaySelected
    )
}

@Composable
fun Day(
    day: LocalDate,
    isSelected: Boolean,
    isCurrentDay: Boolean,
    isSelectable: Boolean,
    onClick: (LocalDate) -> Unit,
) {
    val selectedBackgroundColor: @Composable () -> Color = remember(isSelected) {
        {
            when {
                isSelected -> {
                    MaterialTheme.colors.selectedItemBackground
                }
                else -> {
                    Color.Transparent
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .padding(MaterialTheme.dimens.innerPaddingSmall)
            .clip(MaterialTheme.shapes.xSmall)
            .background(color = selectedBackgroundColor())
            .ifTrue(isCurrentDay) {
                Modifier.border(width = MaterialTheme.dimens.borderMedium, color = MaterialTheme.colors.selectedItemBackground, shape = MaterialTheme.shapes.xSmall)
            }
            .clickable(
                enabled = isSelectable,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when {
            isSelected -> MaterialTheme.colors.primary
            isSelectable -> MaterialTheme.colors.primary
            else -> MaterialTheme.colors.onBackground
        }
        Text(
            text = day.dayOfMonth.toString(),
            color = textColor,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun CalendarHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground,
                text = dayOfWeek.displayText(),
            )
        }
    }
}