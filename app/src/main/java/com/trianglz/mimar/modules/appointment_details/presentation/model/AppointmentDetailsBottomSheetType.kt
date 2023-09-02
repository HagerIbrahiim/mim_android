package com.trianglz.mimar.modules.appointment_details.presentation.model

import com.trianglz.mimar.modules.ratings.presenation.model.ReviewAppointmentSheetData

sealed class AppointmentDetailsBottomSheetType{
    object SingleSelection: AppointmentDetailsBottomSheetType()
    data class SubmitAppointmentReview(val sheetData: ReviewAppointmentSheetData) : AppointmentDetailsBottomSheetType()
    data class ConfirmationMessage(val dataModel: BaseConfirmationInfoModel?): AppointmentDetailsBottomSheetType()
    data class CancellationPolicy(val policy: String): AppointmentDetailsBottomSheetType()
}
