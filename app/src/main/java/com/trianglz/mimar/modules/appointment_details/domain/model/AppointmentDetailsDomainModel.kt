package com.trianglz.mimar.modules.appointment_details.domain.model


import com.trianglz.mimar.modules.addresses.domain.model.CustomerAddressDomainModel
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentDomainModel
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.currency.domain.model.CurrencyDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.payment.domain.model.PaymentStatusType
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel


data class AppointmentDetailsDomainModel(
    override val id: Int?=null,
    override val status: AppointmentStatusType?=null,
    override val date: String?=null,
    override val location: OfferedLocationType?=null,
    override val branchName: String?=null,
    override val appointmentBranchServices: List<ServiceDomainModel>?=null,
    override val paymentMethod: String?=null,
    override val totalTime: String?=null,
    override val appointmentNumber: String?=null,
    override val branch: BranchDomainModel?=null,
    val canCancel: Boolean?=null,
    val currency: CurrencyDomainModel? = null,
    val paymentStatus: PaymentStatusType?=null,
    val paymentOption: String?=null,
    val cancellerId: String?=null,
    val cancellerType: String?=null,
    val allowedPaymentMethods: List<String>?=null,
    val employeeAppointmentProblemReason: Any?=null,
    val customerAppointmentProblemReason: Any?=null,
    val hasReview: Boolean?=null,
    val totalCost: Double?=null,
    val totalCostWithoutVat: Double?=null,
    val vat: Double?=null,
    val vatAmount: Double? = null,
    val customerAddress: CustomerAddressDomainModel?=null,
    ): AppointmentDomainModel()