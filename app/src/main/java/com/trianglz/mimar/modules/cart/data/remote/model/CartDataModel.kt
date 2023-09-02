package com.trianglz.mimar.modules.cart.data.remote.model

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.trianglz.mimar.modules.socket.domain.model.SocketModel
import com.trianglz.mimar.modules.addresses.data.model.CustomerAddressDataModel
import com.trianglz.mimar.modules.branches.data.remote.model.BranchDataModel
import timber.log.Timber

@Keep
data class CartDataModel(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "is_clearable")
    val isClearable: Boolean? = null,
    @Json(name = "appointment_location")
    val appointmentLocation: String? = null,
    @Json(name = "starts_at")
    val startsAt: String? = null,
    @Json(name = "appointment_date")
    val appointmentDate: String? = null,
    @Json(name = "total_estimated_price")
    val totalEstimatedPrice: Double? = null,
    @Json(name = "total_estimated_time")
    val totalEstimatedTime: String? = null,
    @Json(name = "notes")
    val notes: String? = null,
    @Json(name = "branch_id")
    val branchId: Int? = null,
    @Json(name = "branch_name")
    val branchName: String? = null,
    @Json(name = "customer_address")
    val customerAddress: CustomerAddressDataModel? = null,
    @Json(name = "allowed_locations")
    val allowedLocations: List<String>? = null,
    @Json(name = "is_valid")
    val isValid: Boolean? = null,
    @Json(name = "cart_branch_services")
    val cartBranchServices: List<CartBranchServicesDataModel>? = null,
    @Json(name = "cart_branch_services_count")
    val cartBranchServicesCount: Int? = null,
    @Json(name = "is_rescheduling")
    val isRescheduling: Boolean? = null,
    @Json(name = "payment_method")
    val paymentMethod: String? = null,
    @Json(name = "allowed_payment_methods")
    val allowedPaymentMethods: List<String>? = null,
    @Json(name = "branch")
    val branch: BranchDataModel? = null,
    @Json(name = "validation_messages")
    val validationMessages: List<ValidationMessageDataModel>? = null,
): SocketModel {
    companion object {
        fun create(json: String): CartDataModel {
            return try {
                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val adapter = moshi.adapter(CartDataModel::class.java)
                adapter.fromJson(json)!!
            } catch (e: Exception) {
                Timber.tag("CartDataModel").d(e)
                throw Exception()
            }
        }
    }

    override fun create(json: String): SocketModel {
        return Companion.create(json)
    }

    override fun toString(): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(CartDataModel::class.java)
        return adapter.toJson(this)
    }
}