package com.trianglz.mimar.modules.appointment_details.presentation.mapper

import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.addresses.ui.mapper.toUI
import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentDetailsDomainModel
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsScreenMode
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsUIModel
import com.trianglz.mimar.modules.branches.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.payment.domain.model.toPaymentMethodType
import com.trianglz.mimar.modules.payment.presentation.model.PaymentMethodUIModel
import com.trianglz.mimar.modules.services.domain.model.ServiceStatus
import com.trianglz.mimar.modules.services.presentation.mapper.toUI
import com.trianglz.mimar.modules.services.presentation.model.ServiceType
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel


fun AppointmentDetailsDomainModel.toUI(
    mode: AppointmentDetailsScreenMode,
    reportEmployeeDidNotShowClicked: (ServiceUIModel) -> Unit,
    onServiceItemClicked: ((ServiceUIModel) -> Unit)? = null,
    cancelAppointmentClicked: () -> Unit,
    onReportProblemClicked: () -> Unit,
    payNowClicked: () -> Unit,
    reviewBtnClicked: () -> Unit,
    changePaymentMethod: () -> Unit,
    onBranchClick: (id: Int) -> Unit,
    gotItClicked: () -> Unit = {},
    cancellationPolicyClicked: () -> Unit = {},
    ) = AppointmentDetailsUIModel(
    id ?: -1,
    status,
    date,
    location,
    branchName,
    appointmentNumber,
    branch?.toUIModel({},onBranchClick),
    paymentMethod.toPaymentMethodType(),
    totalTime,
    appointmentBranchServices?.mapIndexed { index, service ->
        service.toUI(
            canReport = if (index == 0) false
            else appointmentBranchServices[index - 1].assignedEmployee?.id == service.assignedEmployee?.id
                    && appointmentBranchServices[index - 1].status in setOf(ServiceStatus.Upcoming,ServiceStatus.Ongoing),
            appointmentStatusType = status,
            type = ServiceType.AppointmentService,
            reportEmployeeDidNotShowClicked = reportEmployeeDidNotShowClicked,
            onServiceItemClicked = onServiceItemClicked
        )
    },
    canCancel,
    currency,
    paymentStatus,
    paymentOption,
    cancellerId,
    cancellerType,
    allowedPaymentMethods?.mapIndexed { index, text ->
        PaymentMethodUIModel(
            id = index, title = StringWrapper(text.toPaymentMethodType().displayName),
            icon = R.drawable.ic_cash,
            key = text,
            isChecked = mutableStateOf(text == paymentMethod),
        )
    } ?: listOf(),
    employeeAppointmentProblemReason,
    customerAppointmentProblemReason,
    hasReview,
    totalCost,
    totalCostWithoutVat,
    vat,
    vatAmount,
    customerAddress?.toUI(),
    mode,
    cancelAppointmentClicked = cancelAppointmentClicked,
    payNowClicked = payNowClicked,
    reviewBtnClicked = reviewBtnClicked,
    onReportProblemClicked = onReportProblemClicked,
    changePaymentMethod = changePaymentMethod,
    gotItClicked = gotItClicked,
    cancellationPolicyClicked = cancellationPolicyClicked

)