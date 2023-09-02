package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.models.WorkingHoursUIModel

@Composable
fun WorkingHoursExpandableItem(
    workingHours: () -> List<WorkingHoursUIModel>?,
    isExpanded: () -> MutableState<Boolean>,
    isLoading: () -> Boolean,
    offDayText: Int =  R.string.closed,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current

    ExpandableCard(
        title = { StringWrapper(R.string.working_hours) },
        icon = { ImageVector.vectorResource(R.drawable.ic_clock_outlined) },
        isLoading = isLoading,
        isExpanded = isExpanded,
        modifier = modifier,
    ) {
        if (!isLoading()) {
            HorizontalDivider(padding = PaddingValues(0.dp))


            workingHours()?.forEachIndexed { index, workingHours ->
                val isLastWorkingHours by remember {
                    derivedStateOf {
                        workingHours()?.size?.minus(1) == index
                    }
                }

                if (workingHours.intervals.isNullOrEmpty().not()) {

                    workingHours.intervals?.forEachIndexed { index, _ ->
                        WorkingHoursItem(
                            day = { if (index == 0) workingHours.formattedWeekDay else null },
                            description = {
                                workingHours.getIntervalText(index).getString(context)
                            },
                            showShimmer = isLoading
                        )
                    }
                } else {
                    WorkingHoursItem(
                        day = { workingHours.formattedWeekDay },
                        description = { workingHours.getIntervalText( offDayText = offDayText,).getString(context) },
                        showShimmer = isLoading,
                    )
                }

                if (!isLastWorkingHours) {
                    HorizontalDivider(padding = PaddingValues(0.dp))
                }
            }
        }
    }
}