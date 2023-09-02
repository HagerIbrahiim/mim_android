package com.trianglz.mimar.modules.appointment_details.presentation.model

import com.trianglz.core.domain.extensions.formatDate
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.enums.Locales
import com.trianglz.core.presentation.utils.getAppLocale
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType.*
import com.trianglz.mimar.modules.appointments.presentation.model.AppointmentUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.currency.domain.model.CurrencyDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.payment.domain.model.PaymentMethodType
import com.trianglz.mimar.modules.payment.domain.model.PaymentStatusType
import com.trianglz.mimar.modules.payment.presentation.model.PaymentMethodUIModel
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import com.trianglz.mimar.common.domain.enum.SimpleDateFormatData.DayDate
import com.trianglz.mimar.common.domain.enum.SimpleDateFormatData.YearMonthDayEn


data class AppointmentDetailsUIModel(
    override val id: Int,
    override val status: AppointmentStatusType?=null,
    override val date: String?=null,
    override val location: OfferedLocationType?=null,
    override val branchName: String?=null,
    override val appointmentNumber: String?=null,
    val branch: BranchUIModel?=null,
    val paymentMethod: PaymentMethodType?=null,
    val totalEstimatedTime: String?=null,
    val appointmentBranchServices: List<ServiceUIModel>?=null,
    val canCancel: Boolean?=null,
    val currency: CurrencyDomainModel? = null,
    val paymentStatus: PaymentStatusType?=null,
    val paymentOption: String?=null,
    val cancellerId: String?=null,
    val cancellerType: String?=null,
    val allowedPaymentMethods: List<PaymentMethodUIModel>?=null,
    val employeeAppointmentProblemReason: Any?=null,
    val customerAppointmentProblemReason: Any?=null,
    val hasReview: Boolean?=null,
    val totalCost: Double?=null,
    val totalCostWithoutVat: Double?=null,
    val vat: Double?=null,
    val vatAmount: Double? = null,
    val customerAddress: CustomerAddressUIModel?=null,
    val mode: AppointmentDetailsScreenMode = AppointmentDetailsScreenMode.AppointmentDetails,
    override val showShimmer: Boolean = false,
    val cancelAppointmentClicked: () -> Unit= {},
    val onReportProblemClicked: () -> Unit= {},
    val payNowClicked: () -> Unit= {},
    val reviewBtnClicked: () -> Unit= {},
    val changePaymentMethod: () -> Unit = {},
    val gotItClicked: () -> Unit = {},
    val cancellationPolicyClicked: () -> Unit = {},

    ) : AppointmentUIModel() {

    val showTotalExactFees = status is PendingPayment || status is Completed
    val showSecondaryBtn = status is PendingPayment
            && paymentMethod is PaymentMethodType.Online && paymentStatus is PaymentStatusType.UnPaid

    val secondaryBtnText = if(customerAppointmentProblemReason == null) R.string.report_problem else R.string.problem_has_been_reported
    val primaryBtnText =
        if (mode is AppointmentDetailsScreenMode.AppointmentDetails) when (status) {
            Upcoming -> R.string.cancel
            PendingPayment -> {
                if (paymentMethod == PaymentMethodType.Cash) {
                    if (customerAppointmentProblemReason == null) R.string.report_problem else R.string.problem_has_been_reported
                } else
                    if (paymentStatus is PaymentStatusType.UnPaid) R.string.pay_now
                    else R.string.report_problem
            }
            Completed -> if(hasReview == true) R.string.review_has_been_submitted else R.string.review
            else -> null
        } else R.string.got_it

    val primaryBtnClicked = if(mode is AppointmentDetailsScreenMode.AppointmentDetails)when(status){
        Upcoming -> cancelAppointmentClicked
        PendingPayment -> {
            if(paymentMethod == PaymentMethodType.Cash) {
                if(customerAppointmentProblemReason == null) onReportProblemClicked else null
            } else
                payNowClicked
        }
        Completed -> reviewBtnClicked
        else -> null
    } else gotItClicked

    val isPrimaryBtnEnabled =  if(mode is AppointmentDetailsScreenMode.AppointmentDetails) when(status){
        Upcoming -> canCancel
        PendingPayment -> {
            if(paymentMethod == PaymentMethodType.Cash) {
                customerAppointmentProblemReason == null
            } else
                true
        }
        Completed -> hasReview == false
        else -> true
    } else true

    val isSecondaryBtnEnabled =  customerAppointmentProblemReason == null
    val showEditPaymentMethod = status is PendingPayment && (allowedPaymentMethods?.size ?: 0) > 1

    val secondaryBtnClicked = onReportProblemClicked
    val showActionButtons = primaryBtnText != null

    val appointmentDetailsFirstSectionIcon = if (mode is AppointmentDetailsScreenMode.AppointmentDetails)
        R.drawable.status_icon else R.drawable.id_icon

    val appointmentDetailsFirstSectionTitle = if (mode is AppointmentDetailsScreenMode.AppointmentDetails)
        StringWrapper(status?.name) else StringWrapper {
            this.getString(R.string.appointment_number,appointmentNumber)
    }


    val appointmentDetailsFirstSectionValue = if (mode is AppointmentDetailsScreenMode.AppointmentDetails)
        R.string.appointment_status else R.string.appointment_id

    val appointmentSelectedAddress = if(location is OfferedLocationType.Home)
        customerAddress?.displayedCustomerAddress else branch?.location

    fun formattedCurrency(): String? {
        return if (getAppLocale() == Locales.ARABIC.code)
            currency?.name
        else
            currency?.symbol
    }

    fun getFormattedDate() =
        date.formatDate(YearMonthDayEn, DayDate)

    companion object {
        fun getShimmer() = AppointmentDetailsUIModel(
            id = -1,
            showShimmer = true,
            date = "2023-04-10",
            location = OfferedLocationType.Home,
            branch = BranchUIModel(showShimmer = true, name = "Name"),
            appointmentBranchServices = ServiceUIModel.getShimmerList(),
            paymentMethod = PaymentMethodType.Cash
        )
    }
}