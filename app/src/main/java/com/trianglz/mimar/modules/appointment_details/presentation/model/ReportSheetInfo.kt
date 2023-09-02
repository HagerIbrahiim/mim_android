package com.trianglz.mimar.modules.appointment_details.presentation.model

data class ReportSheetInfo(
    val serviceId: Int? = null,
    val employeeName: String?= null,
    override val confirmationDialogOkClicked: () -> Unit = {},
): BaseConfirmationInfoModel {
    override val confirmationDialogType: ConfirmationDialogType
        get() = ConfirmationDialogType.ReportService(this)
}