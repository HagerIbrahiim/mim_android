package com.trianglz.mimar.modules.appointment_details.presentation.model

interface BaseConfirmationInfoModel  {
   val confirmationDialogType : ConfirmationDialogType
   val confirmationDialogOkClicked: () -> Unit
}
