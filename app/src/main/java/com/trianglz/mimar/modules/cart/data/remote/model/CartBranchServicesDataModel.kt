package com.trianglz.mimar.modules.cart.data.remote.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.mimar.modules.currency.data.model.CurrencyDataModel
import com.trianglz.mimar.modules.employee.data.model.EmployeeDataModel
import com.trianglz.mimar.modules.services.data.model.ServiceDataModel

@Keep
data class CartBranchServicesDataModel(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "notes")
    val notes: Any? = null,
    @Json(name = "order_in_cart")
    val orderInCart: Int? = null,
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "starts_at")
    val startsAt: String? = null,
    @Json(name = "ends_at")
    val endsAt: String? = null,
    @Json(name = "branch_service")
    val branchService: ServiceDataModel? = null,
    @Json(name = "employee")
    val employee: EmployeeDataModel? = null,
    @Json(name = "validation_messages")
    val validationMessages: List<ValidationMessageDataModel>? = null,
    @Json(name = "employee_did_not_show_up")
    val employeeDidNotShowUp: Boolean?=null,
    @Json(name = "has_exact")
    val hasExact: Boolean? = null,
    @Json(name = "exact_fees")
    val exactFees: Double? = null,


    // New attributes in this object (in case Appointment Services Model)
    @Json(name = "fees_from")
    val feesFrom: Double? = null,
    @Json(name = "fees_to")
    val feesTo: Double? = null,
    @Json(name = "duration_mins")
    val durationMins: Int? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "currency")
    val currency: CurrencyDataModel? = null,

    @Json(name = "waiting_time")
    val waitingTime: String? = null,
)