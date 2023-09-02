package com.trianglz.mimar.modules.services.presentation.mapper

import androidx.compose.runtime.mutableStateOf
import com.trianglz.core.domain.enum.SimpleDateFormatEnum
import com.trianglz.core.domain.extensions.formatDate
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType
import com.trianglz.mimar.modules.branches.presentation.mapper.toDomainModel
import com.trianglz.mimar.modules.branches.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.cart.presentation.mapper.toUI
import com.trianglz.mimar.modules.cart.presentation.model.ValidationMessageUIModel
import com.trianglz.mimar.modules.currency.presentation.mapper.toDomain
import com.trianglz.mimar.modules.currency.presentation.mapper.toUI
import com.trianglz.mimar.modules.employee.presentation.mapper.toDomain
import com.trianglz.mimar.modules.employee.presentation.mapper.toUI
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.services.presentation.model.ServiceType
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel

fun ServiceDomainModel.toUI(
    canReport: Boolean?=null,
    appointmentStatusType: AppointmentStatusType?=null,
    type: ServiceType? = ServiceType.ServiceWithAddButton,
    onAddServiceToCart: (ServiceUIModel) -> Unit = {},
    onRemoveFromCart: (ServiceUIModel) -> Unit = {},
    onChangeEmployeeClicked: (ServiceUIModel) -> Unit = {},
    onConflictClicked: (ValidationMessageUIModel) -> Unit = {},
    reportEmployeeDidNotShowClicked: (ServiceUIModel) -> Unit = {},
    onServiceItemClicked: ((ServiceUIModel) -> Unit)?,
    deleteSelectedEmployee: ((ServiceUIModel) -> Unit)? = null,

    ) = ServiceUIModel(
    id =  id,
    serviceIdInCart =  serviceIdInCart,
    feesFrom =  feesFrom,
    feesTo =  feesTo,
    durationMins =  durationMins,
    description =  description,
    offeredLocation =  offeredLocation,
    isActive =  isActive,
    title =  title,
    isAdded =  mutableStateOf(isAdded ?: false),
    currency = currency?.toUI(),
    assignedEmployee = assignedEmployee?.toUI {},
    type = type ?: ServiceType.ServiceWithAddButton,
    onAddServiceToCart = onAddServiceToCart,
    onRemoveFromCart = onRemoveFromCart,
    onChangeEmployeeClicked = onChangeEmployeeClicked,
    onConflictClicked = onConflictClicked,
    reportEmployeeDidNotShowClicked = reportEmployeeDidNotShowClicked,
    status = status,
    startAt = startAt.formatDate(
        SimpleDateFormatEnum.HOUR_MINUTES_24_FORMAT,
        SimpleDateFormatEnum.HOUR_MINUTES_12_FORMAT
    ),
    endAt = endAt.formatDate(
        SimpleDateFormatEnum.HOUR_MINUTES_24_FORMAT,
        SimpleDateFormatEnum.HOUR_MINUTES_12_FORMAT
    ),
    onServiceItemClicked = onServiceItemClicked,
    branch = branch?.toUIModel({},{}),
    branchSpecialtyId = branchSpecialtyId,
    appointmentStatusType = appointmentStatusType,
    canReport = canReport,
    employeeDidNotShowUp = employeeDidNotShowUp,
    hasExact = hasExact,
    exactFees = exactFees,
    validationMessages = validationMessages?.map { it.toUI() },
    /**
     * @param [hasEmployeeValidation]
     * this line of code disabled for now and hard coded hasEmployeeValidation with false that it may be change in the future again from BE
     * this.validationMessages?.any { it.requiredAction == CartValidationActionType.ChangeEmployee.key } ?: false
     */
    hasEmployeeValidation = false,
    hasServiceValidation = (this.validationMessages?.size ?: 0) > 0,
    waitingTime = waitingTime,
    deleteSelectedEmployee = deleteSelectedEmployee,
)

fun ServiceUIModel.toDomain() = ServiceDomainModel(
    id = id,
    serviceIdInCart = serviceIdInCart,
    feesFrom = feesFrom,
    feesTo = feesTo,
    durationMins = durationMins,
    description = description,
    offeredLocation = offeredLocation,
    isActive = isActive,
    title = title,
    isAdded = isAdded.value,
    currency = currency?.toDomain(),
    assignedEmployee = assignedEmployee?.toDomain(),
    branch = branch?.toDomainModel(),
    hasExact = hasExact,
    exactFees = exactFees,
    waitingTime = waitingTime,
    )