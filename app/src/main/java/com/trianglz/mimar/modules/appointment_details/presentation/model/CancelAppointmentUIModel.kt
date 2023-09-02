package com.trianglz.mimar.modules.appointment_details.presentation.model

data class CancelAppointmentUIModel(override val confirmationDialogOkClicked: () -> Unit): BaseConfirmationInfoModel {
    override val confirmationDialogType: ConfirmationDialogType
        get() = ConfirmationDialogType.CancelAppointment
}