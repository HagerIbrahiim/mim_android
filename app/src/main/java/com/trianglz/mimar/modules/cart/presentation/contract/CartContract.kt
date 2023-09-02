package com.trianglz.mimar.modules.cart.presentation.contract

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.contract.BaseEvent
import com.trianglz.core.presentation.contract.BaseState
import com.trianglz.core.presentation.contract.BaseViewState
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.tabs.models.TabItemUIModel
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.authentication.presentation.composables.TextFieldState
import com.trianglz.mimar.modules.calendar.presentation.model.CalendarUIModel
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.presentation.model.*
import com.trianglz.mimar.modules.currency.presentation.model.CurrencyUIModel
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.payment.presentation.model.PaymentMethodUIModel
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import com.trianglz.mimar.modules.time.presentation.model.TimeUIModel
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

// Events that user performed
sealed class CartEvent : BaseEvent {
    object RefreshScreen : CartEvent()
    object OnChangeAddressClicked : CartEvent()
    object OnAddAddressClicked : CartEvent()
    object OnCalendarHeaderClicked : CartEvent()
    object OnAddAnotherServiceClicked : CartEvent()
    object OnConfirmAppointmentClicked : CartEvent()
    object OnOpenDiscoverScreenClicked : CartEvent()
    object OnCloseBottomSheetClicked : CartEvent()
    data class SubmitNotesClicked(val notes: String) : CartEvent()
    data class LocationTabChanged(val index: Int) : CartEvent()
    data class PaymentChanged(val id: Int) : CartEvent()
    data class OnAddressChangedClicked(val customerAddressUIModel: CustomerAddressUIModel) : CartEvent()
    data class OnRemoveServiceFromCartClicked(val serviceUIModel: ServiceUIModel) : CartEvent()
    data class OnConflictClicked(val serviceConflictType: ValidationMessageUIModel) : CartEvent()
    data class OnDateChanged(val date: LocalDate) : CartEvent()
    data class UpdateCart(val cartDomainModel: CartDomainModel) : CartEvent()
    data class NetworkConnectionChanged(val isConnected: Boolean): CartEvent()
    data class RemoveServiceSelectedEmployee(val branchServiceId: Int): CartEvent()

}

sealed class CartState : BaseState {
    object OpenChangeAddressScreen : CartState()
    object OpenAddAddressScreen : CartState()
    object OpenServicesList : CartState()
    object OpenDiscoverScreen : CartState()
    object CloseBottomSheet : CartState()
    data class AskToRemoveServiceFromCart(val serviceUIModel: ServiceUIModel) : CartState()
    data class ChangeEmployee(val serviceUIModel: ServiceUIModel, val offeredLocation: String) : CartState()
    data class SelectDate(val date: LocalDate) : CartState()
    data class ShowConflictDialog(val validationMessage: ValidationMessageUIModel) : CartState()
    data class OpenNoteDialog(val note: String) : CartState()
    data class OpenConfirmationScreen(val appointmentId: Int):  CartState()
    object Finish : CartState()
}

data class CartViewState(
    override val isRefreshing: MutableStateFlow<Boolean> = MutableStateFlow(false),
    override val networkError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var user: MutableState<UserUIModel?> = mutableStateOf(null),
    var cart: MutableState<CartUIModel?> = mutableStateOf(null),
    val selectedOfferedLocationType: MutableState<OfferedLocationType?> = mutableStateOf(null),
    val calendarUIModel: MutableState<CalendarUIModel?> = mutableStateOf(null),
    val showAddressSection: MutableState<Boolean> = mutableStateOf(true),
    val availableTimeSlots: SnapshotStateList<TimeUIModel> = SnapshotStateList(),
    val services: SnapshotStateList<ServiceUIModel> = SnapshotStateList(),
    val canAddAnotherService: MutableState<Boolean> = mutableStateOf(true),
    val noteUIModel: MutableState<NoteUIModel?> = mutableStateOf(null),
    val validationMessage: MutableState<ValidationMessageUIModel?> = mutableStateOf(null),
    val totalEstimatedPrice: MutableState<Double?> = mutableStateOf(null),
    val totalEstimatedTime: MutableState<String?> = mutableStateOf(null),
    val paymentTypes: SnapshotStateList<PaymentMethodUIModel?> = SnapshotStateList(),
    val enableConfirmBtn: MutableState<Boolean> = mutableStateOf(false),
    val currency: MutableState<CurrencyUIModel?> = mutableStateOf(null),
    val selectedAddress: MutableState<CustomerAddressUIModel?> = mutableStateOf(null),
    val availableLocations: SnapshotStateList<TabItemUIModel> = SnapshotStateList(),
    val emptyCart: MutableState<Boolean> = mutableStateOf(false),
    val bottomSheetType: MutableState<CartBottomSheetType?> = mutableStateOf(null),
    var noteBottomSheetState: TextFieldState = TextFieldState(),
    ) : BaseViewState {

    val list: SnapshotStateList<BaseCartUIModel> = SnapshotStateList()
    fun createList() {
        list.add(CartLocationSectionUIModel(availableLocations))
        list.add(CartValidationSectionUIModel(validationMessage))
        list.add(CartCalendarUIModel(calendarUIModel))
        list.add(CartSectionTitleUIModel(title = StringWrapper(R.string.available_slots)))
        list.add(CartAvailableTimesUIModel(availableTimeSlots))
        list.add(CartSectionTitleUIModel(title = StringWrapper(R.string.chosen_services)))
        list.addAll(services)
        list.add(CartAddAnotherServiceUIModel(canAddAnotherService))
        list.add(CartAddressSectionUIModel(selectedAddress, showAddressSection))
        list.add(CartSectionTitleUIModel(title = StringWrapper(R.string.payment_method)))
        list.add(CartPaymentTypesSectionUIModel(paymentTypes))
        list.add(CartNoteSectionUIModel(noteUIModel))
        list.add(CartFooterSectionUIModel(totalEstimatedPrice, totalEstimatedTime, enableConfirmBtn, currency))
    }


}