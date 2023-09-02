package com.trianglz.mimar.modules.appointment_details.data.remote.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.mimar.modules.addresses.data.model.CustomerAddressDataModel
import com.trianglz.mimar.modules.appointments.data.remote.model.AppointmentDataModel
import com.trianglz.mimar.modules.currency.data.model.CurrencyDataModel

@Keep
open class AppointmentDetailsDataModel(
    @Json(name = "payment_status")
    val paymentStatus: String?=null,
    @Json(name = "payment_option")
    val paymentOption: String?=null,
    @Json(name = "canceller_id")
    val cancellerId: String?=null,
    @Json(name = "canceller_type")
    val cancellerType: String?=null,
    @Json(name = "can_cancel")
    val canCancel: Boolean?=null,
    @Json(name = "currency")
    val currency: CurrencyDataModel? = null,
    @Json(name = "allowed_payment_methods")
    var allowedPaymentMethods: List<String>?=null,
    @Json(name = "employee_appointment_problem_reason")
    val employeeAppointmentProblemReason: Any?=null,
    @Json(name = "customer_appointment_problem_reason")
    val customerAppointmentProblemReason: Any?=null,
    @Json(name = "has_review")
    val hasReview: Boolean?=null,
    @Json(name = "total_exact_fees")
    val totalExactFees: Double?= null,
    @Json(name = "total_exact_fees_without_vat")
    val totalExactFeesWithoutVat: Double?= null,
    @Json(name = "vat")
    val vat: Double? = null,
    @Json(name = "vat_amount")
    val vatAmount: Double? = null,
    @Json(name = "customer_address")
    val customerAddress: CustomerAddressDataModel?=null,
) : AppointmentDataModel()