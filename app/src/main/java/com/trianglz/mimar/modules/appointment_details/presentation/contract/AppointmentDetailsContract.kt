package com.trianglz.mimar.modules.appointment_details.presentation.contract

import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.modules.appointment_details.presentation.model.*
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.filter.presenation.model.BaseSelectionUiModel
import com.trianglz.mimar.modules.payment.presentation.model.PaymentCheckoutInfoUIModel
import com.trianglz.mimar.modules.ratings.presenation.model.ReviewAppointmentSheetData
import kotlinx.coroutines.flow.MutableStateFlow

sealed class AppointmentDetailsEvent : BaseEvent {
    object RefreshScreen : AppointmentDetailsEvent()
    object CartIconClicked : AppointmentDetailsEvent()
    object BackIconClicked : AppointmentDetailsEvent()
    object PayNowClicked : AppointmentDetailsEvent()
    object ReportProblemClicked : AppointmentDetailsEvent()
    object OnCloseBottomSheetClicked : AppointmentDetailsEvent()
    object SubmitBottomSheetClicked : AppointmentDetailsEvent()
    object ReviewBtnClicked : AppointmentDetailsEvent()
    object CancelAppointmentBtnClicked : AppointmentDetailsEvent()
    object ChangePaymentMethodClicked : AppointmentDetailsEvent()
    object GotItClicked : AppointmentDetailsEvent()
    object CancellationPolicyClicked : AppointmentDetailsEvent()
    data class CheckArgsIfChanged(val bundle: Bundle) : AppointmentDetailsEvent()
    data class OnConfirmReportEmployeeNoShow(val serviceId: Int): AppointmentDetailsEvent()
    data class BranchCardClicked(val branchId: Int): AppointmentDetailsEvent()
    data class EmployeeDidNotShowUpClicked(val serviceId: Int, val employeeName: String) : AppointmentDetailsEvent()
    data class OnBottomSheetOptionChecked(val option: BaseCheckboxItemUiModel) : AppointmentDetailsEvent()

}

sealed class AppointmentDetailsState : BaseState {
    object FinishScreen : AppointmentDetailsState()
    object OpenSelectionDialog : AppointmentDetailsState()
    object CloseBottomSheet : AppointmentDetailsState()
    object OpenCart : AppointmentDetailsState()
    object OpenHome: AppointmentDetailsState()
    data class ShowConfirmationBottomSheet(val data: BaseConfirmationInfoModel): AppointmentDetailsState()
    data class OpenSubmitAppointmentReview(val sheetData: ReviewAppointmentSheetData): AppointmentDetailsState()
    data class OpenBranchDetails(val branchId: Int): AppointmentDetailsState()
    data class OpenPaymentGateway(val paymentCheckoutInfoUIModel: PaymentCheckoutInfoUIModel) : AppointmentDetailsState()
    data class OpenCancellationPolicy(val policy: String): AppointmentDetailsState()

}

data class AppointmentDetailsViewState(
    val appointment: MutableState<AppointmentDetailsUIModel?> = mutableStateOf(null),
    val selectionSheetData: MutableState<BaseSelectionUiModel?> = mutableStateOf(null),
    val reportSheetData: MutableState<ReportSheetInfo?> = mutableStateOf(null),
    val bottomSheetType: MutableState<AppointmentDetailsBottomSheetType?> = mutableStateOf(null),
    val isBottomSheetOpened: MutableState<Boolean> = mutableStateOf(false),
    var appointmentId: Int? = null,
    var openReviewAppointment: Boolean? = null,
    var mode: AppointmentDetailsScreenMode = AppointmentDetailsScreenMode.AppointmentDetails,
    val appointmentFeedbackTextFieldState: TextFieldState = TextFieldState(),
    var reviewAppointmentSheetData: MutableState<ReviewAppointmentSheetData?> = mutableStateOf(null),
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),

    ) : BaseViewState
