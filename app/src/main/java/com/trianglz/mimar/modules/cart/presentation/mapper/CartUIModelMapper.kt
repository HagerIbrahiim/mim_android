package com.trianglz.mimar.modules.cart.presentation.mapper

import androidx.compose.runtime.mutableStateOf
import com.trianglz.mimar.modules.addresses.ui.mapper.toUI
import com.trianglz.mimar.modules.branches.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.presentation.model.CartUIModel
import com.trianglz.mimar.modules.cart.presentation.model.ValidationMessageUIModel
import com.trianglz.mimar.modules.offered_location.domain.model.toOfferedLocationType
import com.trianglz.mimar.modules.payment.domain.model.toPaymentMethodType
import com.trianglz.mimar.modules.services.presentation.mapper.toUI
import com.trianglz.mimar.modules.services.presentation.model.ServiceType
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel

fun CartDomainModel.toUI(
    type: ServiceType = ServiceType.ServiceWithAddButton,
    onAddServiceToCart: (ServiceUIModel) -> Unit = {},
    onRemoveFromCart: (ServiceUIModel) -> Unit = {},
    onChangeEmployeeClicked: (ServiceUIModel) -> Unit = {},
    onConflictClicked: (ValidationMessageUIModel) -> Unit = {},
    onServiceItemClicked: ((ServiceUIModel) -> Unit)? = null,
    deleteSelectedEmployee: ((ServiceUIModel) -> Unit)? = null,

    ): CartUIModel = CartUIModel(
    id = id,
    isClearable = isClearable,
    appointmentLocation = appointmentLocation.toOfferedLocationType(),
    startsAt = startsAt,
    appointmentDate = appointmentDate,
    totalEstimatedPrice = totalEstimatedPrice,
    totalEstimatedTime = totalEstimatedTime,
    notes = notes,
    branchId = branchId,
    customerAddress = customerAddress?.toUI(),
    allowedLocations = allowedLocations?.map { it.toOfferedLocationType() },
    isValid = isValid,
    cartBranchServicesCount = mutableStateOf(cartBranchServicesCount),
    cartBranchServices = cartBranchServices?.map {
        it.toUI(
            type = type,
            onAddServiceToCart = onAddServiceToCart,
            onRemoveFromCart = onRemoveFromCart,
            onChangeEmployeeClicked = onChangeEmployeeClicked,
            onConflictClicked = onConflictClicked,
            onServiceItemClicked = onServiceItemClicked,
            deleteSelectedEmployee = deleteSelectedEmployee,
            )
    },
    branchName = branchName,
    isRescheduling = isRescheduling,
    paymentMethod = paymentMethod,
    allowedPaymentMethods = allowedPaymentMethods?.map { it.toPaymentMethodType() },
    branch = branch?.toUIModel({}, {}),
    validationMessages = validationMessages?.map { it.toUI() }

)