package com.trianglz.mimar.modules.cart.data.remote.mapper

import com.trianglz.mimar.modules.addresses.data.mapper.toDomain
import com.trianglz.mimar.modules.branches.data.remote.mapper.toDomainModel
import com.trianglz.mimar.modules.cart.data.remote.model.CartBranchServicesDataModel
import com.trianglz.mimar.modules.cart.data.remote.model.CartDataModel
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.currency.data.mapper.toDomain
import com.trianglz.mimar.modules.employee.data.mapper.toDomain
import com.trianglz.mimar.modules.payment.domain.model.toPaymentMethodType
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.services.domain.model.toServiceStatus

fun CartDataModel.toDomain(): CartDomainModel = CartDomainModel(
    id = id,
    isClearable = isClearable,
    appointmentLocation = appointmentLocation,
    startsAt = startsAt,
    appointmentDate = appointmentDate,
    totalEstimatedPrice = totalEstimatedPrice,
    totalEstimatedTime = totalEstimatedTime,
    branchId = branchId,
    notes = notes,
    customerAddress = customerAddress?.toDomain(),
    allowedLocations = allowedLocations,
    isValid = isValid,
    cartBranchServices = cartBranchServices?.map { it.toDomainModel() },
    cartBranchServicesCount = cartBranchServicesCount ?: cartBranchServices?.size,
    branchName = branchName,
    isRescheduling = isRescheduling,
    paymentMethod = paymentMethod.toPaymentMethodType(),
    allowedPaymentMethods = allowedPaymentMethods,
    branch = branch?.toDomainModel(),
    validationMessages = validationMessages?.map { it.toDomain() }
)


fun CartBranchServicesDataModel.toDomainModel(): ServiceDomainModel = ServiceDomainModel(
    id = branchService?.id,
    serviceIdInCart = id,
    feesFrom = branchService?.feesFrom ?: feesFrom,
    feesTo = branchService?.feesTo ?: feesTo,
    durationMins = branchService?.durationMins ?: durationMins,
    startAt = startsAt,
    endAt = endsAt,
    description = branchService?.description ?: description,
    offeredLocation = branchService?.offeredLocation,
    isActive = branchService?.isActive,
    title = branchService?.title ?: title,
    isAdded = branchService?.isInCart,
    currency = branchService?.currency?.toDomain() ?: currency?.toDomain(),
    assignedEmployee = employee?.toDomain(),
    status = status?.toServiceStatus(),
    branchSpecialtyId = branchService?.branchSpecialtyId,
    branch = branchService?.branch?.toDomainModel(),
    validationMessages = validationMessages?.map { it.toDomain() },
    employeeDidNotShowUp = employeeDidNotShowUp,
    hasExact = hasExact,
    exactFees = exactFees,
    waitingTime = waitingTime,
    )