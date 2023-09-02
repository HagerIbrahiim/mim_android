package com.trianglz.mimar.modules.cart.domain.model

import android.os.Parcelable
import com.trianglz.core.domain.model.IdIdentifiedModel
import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.payment.domain.model.PaymentMethodType
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartDomainModel(
    val id: Int? = null,
    val isClearable: Boolean? = null,
    val appointmentLocation: String? = null,
    val startsAt: String? = null,
    val appointmentDate: String? = null,
    val totalEstimatedPrice: Double? = null,
    val totalEstimatedTime: String? = null,
    val notes: String? = null,
    val branchId: Int? = null,
    val branchName: String? = null,
    val customerAddress: CustomerAddressDomainModel? = null,
    val allowedLocations: List<String>? = null,
    val isValid: Boolean? = null,
    @Transient
    val cartBranchServices: List<ServiceDomainModel>? = null,
    val cartBranchServicesCount: Int? = null,
    val isRescheduling: Boolean? = null,
    @Transient
    val paymentMethod: PaymentMethodType? = null,
//    @Transient
    val allowedPaymentMethods: List<String>?,
    val branch: BranchDomainModel?,
    val validationMessages: List<ValidationMessageDomainModel>? = null,
): IdIdentifiedModel, Parcelable {
    override val uniqueId: Int
        get() = id ?: System.identityHashCode(this)
}
