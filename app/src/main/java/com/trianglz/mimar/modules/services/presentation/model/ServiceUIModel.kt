package com.trianglz.mimar.modules.services.presentation.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.stringResource
import com.trianglz.core_compose.presentation.pagination.model.PaginatedModel
import com.trianglz.mimar.R
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType.*
import com.trianglz.mimar.modules.cart.presentation.model.BaseCartUIModel
import com.trianglz.mimar.modules.branches.presentation.model.BranchUIModel
import com.trianglz.mimar.modules.cart.presentation.model.ValidationMessageUIModel
import com.trianglz.mimar.modules.search.presentation.model.BaseSearchModel
import com.trianglz.mimar.modules.currency.presentation.model.CurrencyUIModel
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import com.trianglz.mimar.modules.services.domain.model.ServiceStatus


data class ServiceUIModel(
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
    val isAdded: MutableState<Boolean> = mutableStateOf(false),
    val currency: CurrencyUIModel?=null,
    val assignedEmployee: EmployeeUIModel? = null,
    val type: ServiceType = ServiceType.ServiceWithAddButton,
    val branch: BranchUIModel? = null,
    val branchSpecialtyId: Int? = null,
    val canReport: Boolean?=null,
    val hasExact: Boolean? = null,
    val exactFees: Double? = null,
    override val showShimmer: Boolean = false,
    val hasEmployeeValidation: Boolean = false,
    val hasServiceValidation: Boolean = false,
    val waitingTime: String? = null,
//    val hasTimeConflict: Boolean? = false,
    val status: ServiceStatus? = null,
    val validationMessages: List<ValidationMessageUIModel>? = null,
    val onConflictClicked: (ValidationMessageUIModel) -> Unit = {},
    val employeeDidNotShowUp: Boolean?=null,
    val appointmentStatusType: AppointmentStatusType?=null,
    val onAddServiceToCart: (ServiceUIModel) -> Unit = {},
    val onRemoveFromCart: (ServiceUIModel) -> Unit = {},
    val onChangeEmployeeClicked: (ServiceUIModel) -> Unit = {},
    val reportEmployeeDidNotShowClicked: (ServiceUIModel) -> Unit = {},
    val onServiceItemClicked: ((ServiceUIModel) -> Unit)? = null,
    val deleteSelectedEmployee: ((ServiceUIModel) -> Unit)? = null,
): PaginatedModel, BaseSearchModel, BaseCartUIModel {

    val showReportIcon = appointmentStatusType is Ongoing
            && status in setOf(ServiceStatus.Upcoming, ServiceStatus.Ongoing)  && canReport == false


    val showStatusRow = appointmentStatusType !is Upcoming
            && status !in setOf(ServiceStatus.Upcoming, ServiceStatus.Ongoing)

    val showEmployeeRow = type is ServiceType.CartService || type is ServiceType.AppointmentService

    @Composable
    fun getFees(): String {

        val formattedFeesFrom = getPriceWithCurrency(price = feesFrom)

        return if (hasExact == true)
            getPriceWithCurrency(price = exactFees)
        else if (feesTo != feesFrom && feesTo != null) {
            formattedFeesFrom +
                    " - " +
                    getPriceWithCurrency(price = feesTo)
        } else {
            formattedFeesFrom
        }
    }

    private val serviceCurrency =
        currency?.displayedCurrency

    @Composable
    fun getPriceWithCurrency(price: Double?) =
        stringResource(
            id = R.string.price_with_currency,
            price ?: 0.0,
            serviceCurrency ?: stringResource(id = R.string.saudi_riyal)
        )

    override val uniqueId: Int
        get() = id ?: -1
    companion object {

        fun getShimmerList(): SnapshotStateList<ServiceUIModel> {
            val list: SnapshotStateList<ServiceUIModel> = SnapshotStateList()
            repeat(20) {
                list.add(
                    ServiceUIModel(
                        id = it,
                        serviceIdInCart = it,
                        showShimmer = true,
                    )
                )
            }
            return list
        }
    }


}