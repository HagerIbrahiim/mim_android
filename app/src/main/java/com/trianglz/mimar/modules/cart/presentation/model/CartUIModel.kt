package com.trianglz.mimar.modules.cart.presentation.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.model.IdIdentifiedModel
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.cart.domain.model.ValidationMessageDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.payment.domain.model.PaymentMethodType
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel

data class CartUIModel(
    val id: Int? = null,
    val isClearable: Boolean? = null,
    val appointmentLocation: OfferedLocationType? = null,
    val startsAt: String? = null,
    val appointmentDate: String? = null,
    val totalEstimatedPrice: Double? = null,
    val totalEstimatedTime: String? = null,
    val notes: String? = null,
    val branchId: Int? = null,
    val customerAddress: CustomerAddressUIModel? = null,
    val allowedLocations: List<OfferedLocationType>? = null,
    val isValid: Boolean? = null,
    val cartBranchServices: List<ServiceUIModel>? = null,
    val cartBranchServicesCount: MutableState<Int?> = mutableStateOf(0),
    val branchName: String? = null,
    val isRescheduling: Boolean? = null,
    val paymentMethod: PaymentMethodType? = null,
    val allowedPaymentMethods: List<PaymentMethodType>?,
    val branch: BranchUIModel?,
    val validationMessages: List<ValidationMessageUIModel>? = null,
): IdIdentifiedModel {
    fun hasChanges(new: CartUIModel?): Boolean {
        return (
                new?.isRescheduling == isRescheduling &&
                new?.appointmentLocation?.key == appointmentLocation?.key &&
                new?.startsAt == startsAt &&
                new?.appointmentDate == appointmentDate &&
                new?.notes == notes &&
                new?.id == id &&
                new?.isClearable == isClearable &&
                new?.totalEstimatedPrice == totalEstimatedPrice &&
                new?.totalEstimatedTime == totalEstimatedTime &&
                new?.branchId == branchId &&
                new?.customerAddress?.uniqueId == customerAddress?.uniqueId &&
                new?.isValid == isValid &&
                new?.cartBranchServicesCount == cartBranchServicesCount &&
                new.paymentMethod == paymentMethod &&
                new.validationMessages?.size == validationMessages?.size

                )
    }

    override val uniqueId: Int
        get() = id ?: System.identityHashCode(this)
}
