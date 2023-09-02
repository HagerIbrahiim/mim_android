package com.trianglz.mimar.common.presentation.compose_views

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.DialogProperties
import com.ramcosta.composedestinations.spec.DestinationStyle.Dialog.Default.properties
import com.trianglz.mimar.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogButtons
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerColors
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TimePickerDialog(
    timeDialogState: () -> MaterialDialogState,
    initialTime: () -> LocalTime,
    timePickerColors: @Composable () -> TimePickerColors = {
        TimePickerDefaults.colors(
        activeBackgroundColor = MaterialTheme.colors.primary,
    )},
    buttonTextStyle: @Composable () -> TextStyle = { MaterialTheme.typography.body1.copy(
        color = MaterialTheme.colors.primary
    )},
    onPositiveBtnClicked: () -> Unit ={},
    onTimeChange: (LocalTime) -> Unit,

) {

    MaterialDialog(
        dialogState = timeDialogState(),
        buttons = {
            DefaultDateTimeDialogButtons(buttonTextStyle,onPositiveBtnClicked)
        }
    ) {
        timepicker(
            colors = timePickerColors(),
            initialTime = initialTime(),
            title = stringResource(id = R.string.pick_time),
            onTimeChange = onTimeChange,
        )
    }
}

@Composable
fun DatePickerDialog(
    dateDialogState: () -> MaterialDialogState,
    initialDate: () -> LocalDate,
    yearRange: IntRange,
    buttonTextStyle: @Composable () -> TextStyle = { MaterialTheme.typography.body1.copy(
        color = MaterialTheme.colors.primary
    )},
    onPositiveBtnClicked: () -> Unit = {},
    onDateChange: (LocalDate) -> Unit,
) {

    MaterialDialog(
        dialogState = dateDialogState(),
        buttons = {
            DefaultDateTimeDialogButtons(buttonTextStyle,onPositiveBtnClicked)
        },

    ) {
        datepicker(
            colors = DatePickerDefaults.colors(
                dateInactiveTextColor = MaterialTheme.colors.primary,
                calendarHeaderTextColor = MaterialTheme.colors.primary,
            ),
            initialDate = initialDate(),
            title = stringResource(id = R.string.pick_date),
            onDateChange = onDateChange,
            yearRange = yearRange
        )
    }
}


@Composable
private fun MaterialDialogButtons.DefaultDateTimeDialogButtons(
    buttonTextStyle: @Composable () -> TextStyle = { MaterialTheme.typography.body1.copy(
        color = MaterialTheme.colors.primary
    )},
    onPositiveBtnClicked: () -> Unit,
) {
    positiveButton(
        text = stringResource(id = R.string.ok),
        textStyle = buttonTextStyle(), onClick = onPositiveBtnClicked
    )
    negativeButton(
        text = stringResource(id = R.string.cancel),
        textStyle = buttonTextStyle()
    )
}