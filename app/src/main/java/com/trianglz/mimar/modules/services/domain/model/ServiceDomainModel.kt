package com.trianglz.mimar.modules.services.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.mimar.modules.branches.domain.model.BranchDomainModel
import com.trianglz.mimar.modules.branches.data.remote.model.BranchDataModel
import com.trianglz.mimar.modules.cart.data.remote.model.ValidationMessageDataModel
import com.trianglz.mimar.modules.cart.domain.model.ValidationMessageDomainModel
import com.trianglz.mimar.modules.currency.domain.model.CurrencyDomainModel
import com.trianglz.mimar.modules.employee.domain.model.EmployeeDomainModel
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class ServiceDomainModel(
    val id: Int? = null,
    val serviceIdInCart: Int? = null,
    val feesFrom: Double? = null,
    val feesTo: Double? = null,
    val durationMins: Int? = null,
    val startAt: String? = null,
    val endAt: String? = null,
    val description: String? = null,
    val offeredLocation: String? = null,
    val isActive: Boolean? = null,
    val title: String? = null,
    val isAdded: Boolean? = null,
    val currency: CurrencyDomainModel?=null,
    val assignedEmployee: EmployeeDomainModel? = null,
    val status: ServiceStatus? = null,
    val branch: BranchDomainModel? = null,
    val branchSpecialtyId: Int? = null,
    val validationMessages: List<ValidationMessageDomainModel>? = null,
    val employeeDidNotShowUp: Boolean?=null,
    val hasExact: Boolean? = null,
    val exactFees: Double? = null,
    val waitingTime: String? = null,
    ) : BaseUpdatableItem, Parcelable {
    override val uniqueId: Int
        get() = id ?: -1
}