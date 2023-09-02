package com.trianglz.mimar.modules.appointments.domain.model

import com.trianglz.core.domain.model.IdIdentifiedModel
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.user.domain.model.UserDomainModel

open class AppointmentDomainModel(
    open val id: Int?=null,
    open val date: String?=null,
    open val startsAt: String?=null,
    open val isCustomerConfirmed: Boolean?=null,
    open val location: OfferedLocationType?=null,
    open val paymentMethod: String?=null,
    open val status: AppointmentStatusType?=null,
    open val totalTime: String?=null,
    open val appointmentBranchServices: List<ServiceDomainModel>?=null,
    open val customer: UserDomainModel?=null,
    open val branch: BranchDomainModel?=null,
    open val serviceProvider: BranchDomainModel?=null,
    open val appointmentNumber: String?=null,
    open val branchName: String? = null,
): IdIdentifiedModel {
    override val uniqueId: Int
        get() = id ?: System.identityHashCode(this)
}
