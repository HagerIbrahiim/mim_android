package com.trianglz.mimar.modules.appointments.data.remote.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.trianglz.mimar.modules.branches.data.remote.model.BranchDataModel
import com.trianglz.mimar.modules.cart.data.remote.model.CartBranchServicesDataModel
import com.trianglz.mimar.modules.user.data.model.UserDataModel

@Keep
open class AppointmentDataModel(
    @property:Json(name= "id")
    open var id: Int?=null,
    @property:Json(name= "date")
    open var date: String?=null,
    @property:Json(name= "starts_at")
    open var startsAt: String?=null,
    @property:Json(name= "is_customer_confirmed")
    open var isCustomerConfirmed: Boolean?=null,
    @property:Json(name= "location")
    open var location: String?=null,
    @property:Json(name= "payment_method")
    open var paymentMethod: String?=null,
    @property:Json(name= "status")
    open var status: String?=null,
    @property:Json(name= "total_estimated_price")
    open var totalEstimatedPrice: Double?=null,
    @property:Json(name= "total_estimated_time")
    open var totalEstimatedTime: String?=null,
    @property:Json(name= "appointment_branch_services")
    open var appointmentBranchServices: List<CartBranchServicesDataModel>?=null,
    @property:Json(name= "customer")
    open var customer: UserDataModel?=null,
    @property:Json(name= "branch")
    open var branch: BranchDataModel?=null,
    @property:Json(name= "service_provider")
    open var serviceProvider: BranchDataModel?=null,
    @property:Json(name= "appointment_number")
    open var appointmentNumber: String?=null,
    @property:Json(name= "branch_name")
    open var branchName: String?=null,

    )