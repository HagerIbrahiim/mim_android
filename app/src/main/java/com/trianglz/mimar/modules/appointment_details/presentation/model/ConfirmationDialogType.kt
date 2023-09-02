package com.trianglz.mimar.modules.appointment_details.presentation.model


import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.trianglz.mimar.R

sealed class ConfirmationDialogType(val title: Int) {
    object CancelAppointment: ConfirmationDialogType(R.string.cancel)
    data class ReportService (val data: ReportSheetInfo): ConfirmationDialogType(R.string.report)
}

@Composable
fun ConfirmationDialogType.getMessage(
    textStyle : SpanStyle = SpanStyle(
    color = MaterialTheme.colors.primary,
    fontWeight = FontWeight.W500,
)): AnnotatedString {
    return when(this){
        is ConfirmationDialogType.ReportService ->{
            buildAnnotatedString {
                withStyle(style = textStyle) {
                    append(stringResource(id = R.string.do_you_want_report_that_employee))
                }

                withStyle(style = textStyle.copy(fontWeight = FontWeight.W600)) {
                    append(" (${data.employeeName}) ")
                }
                withStyle(style = textStyle) {
                    append(stringResource(id = R.string.did_not_show_up))
                }
            }
        }
        else -> {
            buildAnnotatedString {
                withStyle(style = textStyle) {
                    append(stringResource(id = R.string.do_you_want_cancel_appointment))
                }
            }
        }
    }
}