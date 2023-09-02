package com.trianglz.mimar.modules.calendar.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.trianglz.core_compose.presentation.compose_ui.BaseDimens.Companion.dimens
import com.trianglz.core_compose.presentation.extensions.clickWithThrottle
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.ui.theme.iconSizeXXXSmall
import com.trianglz.mimar.common.presentation.ui.theme.xxSmall
import java.time.LocalDate


@Composable
fun WeekCalendarTitle(
    weekState: WeekCalendarState,
    onClick: (LocalDate) -> Unit,
) {
    val visibleWeek = rememberFirstVisibleWeekAfterScroll(weekState)
    CalendarTitle(
        modifier = Modifier,
        currentMonth = getWeekPageTitle(visibleWeek),
        onClick = { onClick.invoke(visibleWeek.days.first().date) },
    )
}

@Composable
fun CalendarTitle(
    modifier: Modifier,
    currentMonth: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(horizontal = MaterialTheme.dimens.screenGuideXSmall)
            .background(Color.Transparent, shape = MaterialTheme.shapes.xxSmall)
            .clip(shape = MaterialTheme.shapes.xxSmall)
            .clickWithThrottle { onClick() }
            .padding(all = MaterialTheme.dimens.spaceBetweenItemsXSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.innerPaddingDefault)
    ) {

        Text(
            modifier = Modifier,
            text = currentMonth,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.W600,
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_down),
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .requiredSizeIn(MaterialTheme.dimens.iconSizeXXXSmall)
                .clip(CircleShape),
        )

    }
}




@Composable
fun MonthAndWeekCalendarTitle(
    isWeekMode: Boolean,
    currentMonth: String,
    monthState: CalendarState,
    weekState: WeekCalendarState,
) {
    val coroutineScope = rememberCoroutineScope()
    CalendarTitle(
        modifier = Modifier,
        currentMonth = currentMonth,
        onClick = {
//            coroutineScope.launch {
//                if (isWeekMode) {
//                    val targetDate = weekState.firstVisibleWeek.days.first().date.minusDays(1)
//                    weekState.animateScrollToWeek(targetDate)
//                } else {
//                    val targetMonth = monthState.firstVisibleMonth.yearMonth.previousMonth
//                    monthState.animateScrollToMonth(targetMonth)
//                }
//            }
        },
//        goToNext = {
//            coroutineScope.launch {
//                if (isWeekMode) {
//                    val targetDate = weekState.firstVisibleWeek.days.last().date.plusDays(1)
//                    weekState.animateScrollToWeek(targetDate)
//                } else {
//                    val targetMonth = monthState.firstVisibleMonth.yearMonth.nextMonth
//                    monthState.animateScrollToMonth(targetMonth)
//                }
//            }
//        },
    )
}