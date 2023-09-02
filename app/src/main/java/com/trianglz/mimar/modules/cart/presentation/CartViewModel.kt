package com.trianglz.mimar.modules.cart.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.text.input.TextFieldValue
import com.trianglz.core.domain.extensions.toIsoFormat
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.mimar.common.domain.extention.getLocalDateFromISO
import com.trianglz.mimar.common.presentation.base.GeneralUpdatesViewModel
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.appointments.domain.usecase.CreateAppointmentUseCase
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchAvailableSlotsUseCase
import com.trianglz.mimar.modules.calendar.presentation.mapper.getOpenWeekDays
import com.trianglz.mimar.modules.calendar.presentation.mapper.toDayOfWeekModel
import com.trianglz.mimar.modules.calendar.presentation.model.CalendarUIModel
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.domain.model.UpdateCartDomainModel
import com.trianglz.mimar.modules.cart.domain.usecase.ConnectGetCartUpdatesUseCase
import com.trianglz.mimar.modules.cart.domain.usecase.DisconnectGetCartUpdatesUseCase
import com.trianglz.mimar.modules.cart.domain.usecase.GetCartUseCase
import com.trianglz.mimar.modules.cart.domain.usecase.ReconnectCartSocketUseCase
import com.trianglz.mimar.modules.cart.domain.usecase.UpdateBranchServiceInCartUseCase
import com.trianglz.mimar.modules.cart.domain.usecase.UpdateCartInUserUseCase
import com.trianglz.mimar.modules.cart.domain.usecase.UpdateCartUseCase
import com.trianglz.mimar.modules.cart.presentation.contract.CartEvent
import com.trianglz.mimar.modules.cart.presentation.contract.CartState
import com.trianglz.mimar.modules.cart.presentation.contract.CartViewState
import com.trianglz.mimar.modules.cart.presentation.mapper.toUI
import com.trianglz.mimar.modules.cart.presentation.model.CartAddAnotherServiceUIModel
import com.trianglz.mimar.modules.cart.presentation.model.NoteUIModel
import com.trianglz.mimar.modules.cart.presentation.model.ValidationMessageUIModel
import com.trianglz.mimar.modules.currency.presentation.model.CurrencyUIModel
import com.trianglz.mimar.modules.notification.domain.manager.ForegroundNotificationManager
import com.trianglz.mimar.modules.notification.domain.model.NotificationType
import com.trianglz.mimar.modules.offered_location.domain.model.OfferedLocationType
import com.trianglz.mimar.modules.offered_location.domain.model.toTabUIModel
import com.trianglz.mimar.modules.payment.domain.model.PaymentMethodType
import com.trianglz.mimar.modules.payment.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.services.domain.model.ServiceStatus
import com.trianglz.mimar.modules.services.domain.usecase.GetServicesUpdates
import com.trianglz.mimar.modules.services.presentation.mapper.toDomain
import com.trianglz.mimar.modules.services.presentation.model.ServiceType
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import com.trianglz.mimar.modules.time.presentation.mapper.toUIModel
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    getServicesUpdates: GetServicesUpdates,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val getBranchAvailableSlotsUseCase: GetBranchAvailableSlotsUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val createAppointmentUseCase: CreateAppointmentUseCase,
    val userModeHandler: UserModeHandler,
    private val connectGetCartUpdatesUseCase: ConnectGetCartUpdatesUseCase,
    private val updateCartInUserUseCase: UpdateCartInUserUseCase,
    private val notificationManager: ForegroundNotificationManager,
    private val reconnectCartSocketUseCase: ReconnectCartSocketUseCase,
    private val disconnectGetCartUpdatesUseCase: DisconnectGetCartUpdatesUseCase,
    private val updateBranchServiceInCartUseCase: UpdateBranchServiceInCartUseCase,

    ) : GeneralUpdatesViewModel<CartEvent, CartViewState, CartState>(getUserUpdatesUseCase, getServicesUpdates) {

    private val branchId get() = viewStates?.cart?.value?.branchId
    private val cartValue get() = viewStates?.cart?.value
    private var isCalendarObserverInitialized = false

    init {
        startListenForUserUpdates()
        socketObserver()
        handleCartObserver()
        getCart()
        observeInternetConnection()
        viewStates?.createList()
    }

    private fun socketObserver() {
        launchCoroutine {
            connectGetCartUpdatesUseCase.execute().distinctUntilChanged { old, new -> old.toUI().hasChanges(new.toUI()) }.collectLatest { cart ->
//                delay(5000)
                updateCartInUserUseCase.execute(cart)
                refreshCart(cart)
                Log.d("test_web_socket", "socketObserver: ${cart} and viewModel = ${this@CartViewModel}")
                // process message here
            }
        }
    }

    private fun handlePaymentTypes(allowedPaymentMethods: List<PaymentMethodType>?) {
//        if (viewStates?.paymentTypes?.isEmpty() == true) {
        viewStates?.paymentTypes?.clear()
        allowedPaymentMethods?.map { it.toUIModel(cartValue?.paymentMethod?.key == it.key) }
            ?.let { viewStates?.paymentTypes?.addAll(it) }
//        }
//        val oldPayment = viewStates?.paymentTypes?.find { it?.isChecked?.value == true }
//        oldPayment?.isChecked?.value = false
//        val newSelectedPayment =
//            viewStates?.paymentTypes?.find { it?.key == cartValue?.paymentMethod?.key }
//        newSelectedPayment?.isChecked?.value = true
    }

    private fun handleCartObserver() {
        launchCoroutine {
            snapshotFlow {
                viewStates?.cart?.value
            }.distinctUntilChanged { old, new -> old?.hasChanges(new) ?: false }.collectLatest {
                Log.d("test_web_socket", "CART _ socketObserver: assignedEmployee = ${it?.cartBranchServices?.firstOrNull()?.assignedEmployee}")

//                viewStates?.emptyCart?.value = it == null

//                if (viewStates?.emptyCart?.value == false) {
                    it?.let {
                        if (it.isRescheduling == true) {
                            setLoading()
                        } else {
                            handleAddressSection(it.appointmentLocation, it.customerAddress)
                            handleOfferedLocation(it.appointmentLocation, it.allowedLocations)
                            updateTimeSlotWithStartTime(it.startsAt)
                            getServices(it.cartBranchServices)
                            handleFooterSection(
                                it.totalEstimatedPrice,
                                it.totalEstimatedTime,
                                it.cartBranchServices?.firstOrNull()?.currency
                            )
                            handleNote(it.notes)
                            handlePaymentTypes(it.allowedPaymentMethods)
                            initializeCalendar(it.appointmentDate, it.branch?.offDays ?: emptyList())
                            checkCartValidation(it.validationMessages)
                            setDoneLoading()
//                        _loadingState.value = AsyncState.Loading
                        }
//                    if (it.isRescheduling == true) {
//                        setLoading()
//                    } else {
//                        setDoneLoading()
//                    }
                    }

//                } else {
//                    resetViewStates()
//                    setDoneLoading()
//                }
            }
        }

    }

    private fun handleAddressSection(
        appointmentLocation: OfferedLocationType?,
        customerAddress: CustomerAddressUIModel?
    ) {
        if (appointmentLocation is OfferedLocationType.Home) {
//            changeAddress(customerAddress)
            viewStates?.selectedAddress?.value = customerAddress
            viewStates?.showAddressSection?.value = true
        } else {
            changeAddress(null)
            viewStates?.showAddressSection?.value = false
        }
    }

    private fun getCart() {
        launchCoroutine {
            setLoading()
            resetViewStates()
            val cart = getCartUseCase.execute()
            if(cart != null) {
                refreshCart(cart)
            } else {
                viewStates?.emptyCart?.value = true
                resetViewStates()
                setDoneLoading()
            }

//            viewStates?.isRefreshing?.value = false

            Log.d("test_cart", "getCart: $cart")
//            setDoneLoading()
        }
    }

    private fun refreshCart(cart: CartDomainModel) {
        viewStates?.cart?.value = cart.toUI(
            type = ServiceType.CartService,
            onRemoveFromCart = {
                setState { CartState.AskToRemoveServiceFromCart(it) }
            },
            onChangeEmployeeClicked = {
                setState {
                    CartState.ChangeEmployee(
                        it, viewStates?.selectedOfferedLocationType?.value?.key ?: ""
                    )
                }
            },
            onConflictClicked = {
                setState { CartState.ShowConflictDialog(it) }
            },
            deleteSelectedEmployee = {
                it.serviceIdInCart?.let {
                    setEvent(CartEvent.RemoveServiceSelectedEmployee(it))
                }
            }
        )
    }

    private fun handleOfferedLocation(
        appointmentLocation: OfferedLocationType?,
        allowedLocations: List<OfferedLocationType>?
    ) {
        if (viewStates?.availableLocations?.isEmpty() == true || allowedLocations?.size != viewStates?.availableLocations?.size || viewStates?.selectedOfferedLocationType?.value?.key != appointmentLocation?.key) {
            allowedLocations?.let { list ->
                viewStates?.availableLocations?.clear()
                viewStates?.availableLocations?.addAll(list.map {
                    it.toTabUIModel(
                        isSelected = mutableStateOf(
                            appointmentLocation == it
                        )
                    )
                })
                viewStates?.selectedOfferedLocationType?.value = appointmentLocation

            }
        }
    }

    private fun handleFooterSection(
        totalEstimatedPrice: Double?,
        totalEstimatedTime: String?,
        currency: CurrencyUIModel?
    ) {
        viewStates?.totalEstimatedPrice?.value = totalEstimatedPrice
        viewStates?.totalEstimatedTime?.value = totalEstimatedTime
        viewStates?.currency?.value = currency
    }

    private fun handleNote(notes: String?) {
        viewStates?.noteUIModel?.value = NoteUIModel(
            mutableStateOf(notes ?: "")
        ) {
            viewStates?.noteBottomSheetState?.textFieldValue?.value = TextFieldValue(
                cartValue?.notes ?: ""
            )
            setState { CartState.OpenNoteDialog(it) }
        }
    }

    private fun getServices(cartBranchServices: List<ServiceUIModel>?) {
        cartBranchServices?.let {
            viewStates?.list?.removeAll { it is ServiceUIModel }
            if (it.isEmpty()) {
                viewStates?.emptyCart?.value = true
                return
            }
            val getIndex = viewStates?.list?.indexOfFirst { it is CartAddAnotherServiceUIModel }
            if (getIndex != null) {
                viewStates?.list?.addAll(
                    getIndex, cartBranchServices
                )
            }

            viewStates?.canAddAnotherService?.value =
                cartBranchServices.lastOrNull()?.status != ServiceStatus.Unscheduled

        }
    }

    private fun initializeCalendar(appointmentDate: String?, offDays: List<String>) {
        val appointmentLocalDate = appointmentDate.getLocalDateFromISO()
        val currentDate = LocalDate.now()
        val offWeekDays = offDays.map { it.toDayOfWeekModel() }

        val openDays =  offWeekDays.getOpenWeekDays()

        val selectedDay =
            if (appointmentLocalDate?.isBefore(currentDate) == false) appointmentLocalDate else if (openDays.contains(
                    currentDate.dayOfWeek
                )
            ) currentDate else null

        if (!isCalendarObserverInitialized) {
            isCalendarObserverInitialized = true

            viewStates?.calendarUIModel?.value = CalendarUIModel(
                openDays = openDays,
                selectedDay = mutableStateOf(selectedDay),
                onTitleClicked = {
                    setState { CartState.SelectDate(it) }
                }
            )
            calendarObservers()
        } else {
            viewStates?.calendarUIModel?.value?.selectedDay?.value = selectedDay
        }


    }

    private fun updateTimeSlotWithStartTime(startsAt: String?) {
        val newItem = viewStates?.availableTimeSlots?.find { it.title == startsAt }
        val oldItem = viewStates?.availableTimeSlots?.find { it.isSelected.value }
        if (newItem != oldItem) {
            viewStates?.availableTimeSlots?.forEach { it.isSelected.value = false }
            newItem?.isSelected?.value = true
        }
    }
    private fun onNewTimeSelected(id: Int) {
        val newItem = viewStates?.availableTimeSlots?.find { it.uniqueId == id }
        val oldItem = viewStates?.availableTimeSlots?.find { it.isSelected.value }
        if (newItem != oldItem) {
            updateCart(firstServiceTime = newItem?.title) {
                viewStates?.availableTimeSlots?.forEach { it.isSelected.value = false }
                newItem?.isSelected?.value = true
            }
        }

    }

    private fun calendarObservers() {
        launchCoroutine {
            snapshotFlow {
                viewStates?.calendarUIModel?.value?.selectedDay?.value
            }.distinctUntilChanged { old, new -> old?.toIsoFormat() == new?.toIsoFormat() }.filterNotNull().collectLatest {
                scrollToTargetDate(it)
                if (it.toIsoFormat() != cartValue?.appointmentDate) {
                    updateCart(appointmentDate = it.toIsoFormat()) {
                        getFreeSlots(it.toIsoFormat())
                    }
                } else {
                    getFreeSlots(it.toIsoFormat())
                }
                Log.d("test_calendar", "calendarObservers: OnDateSelected = $it")

            }
        }
    }

    private fun getFreeSlots(date: String) {
        branchId?.let { id ->
            launchCoroutine {
//                setLoading()
                val availableTimeSlots =
                    getBranchAvailableSlotsUseCase.execute(branchId = id, date = date).map {
                        it.toUIModel { newTimeId ->
                            onNewTimeSelected(newTimeId)
                        }
                    }
                viewStates?.availableTimeSlots?.clear()
                delay(30)
                viewStates?.availableTimeSlots?.addAll(availableTimeSlots)
//                setDoneLoading()
            }
        }

    }

//    private fun getUserData() {
//        launchCoroutine {
//            user.collect {
//                viewStates?.user?.value = it
//            }
//        }
//    }


    override fun createInitialViewState(): CartViewState {
        return CartViewState()
    }

    override fun handleEvents(event: CartEvent) {
        when (event) {
            CartEvent.OnChangeAddressClicked -> {
                setState { CartState.OpenChangeAddressScreen }
            }
            CartEvent.RefreshScreen -> {
                getCart()
            }
            CartEvent.OnCalendarHeaderClicked -> {

            }
            is CartEvent.OnDateChanged -> {
                scrollToTargetDate(event.date)
            }
            is CartEvent.OnRemoveServiceFromCartClicked -> {
                toggleUpdatableItem(event.serviceUIModel.toDomain(), true)
            }
            CartEvent.OnAddAnotherServiceClicked -> {
                setState { CartState.OpenServicesList }
            }
            is CartEvent.OnConflictClicked -> {
                setState { CartState.ShowConflictDialog(event.serviceConflictType) }
            }
            CartEvent.OnConfirmAppointmentClicked -> {
                confirmAppointment()
            }
            CartEvent.OnAddAddressClicked -> {
                setState { CartState.OpenAddAddressScreen }
            }
            CartEvent.OnCloseBottomSheetClicked -> {
                setState { CartState.CloseBottomSheet }
            }
            is CartEvent.OnAddressChangedClicked -> {
                changeAddress(event.customerAddressUIModel)

            }
            is CartEvent.LocationTabChanged -> {
                handleChangedAppointmentLocation(event.index)
            }
            CartEvent.OnOpenDiscoverScreenClicked -> {
                setState { CartState.OpenDiscoverScreen }
            }
            is CartEvent.SubmitNotesClicked -> {
                updateNotes(event.notes)
            }
            is CartEvent.UpdateCart -> {
                //TODO Remove this state as socket handle it
//                refreshCart(event.cartDomainModel)
            }
            is CartEvent.PaymentChanged -> {
                handleChangedPayment(event.id)
            }

            is CartEvent.NetworkConnectionChanged -> {
                if(event.isConnected){
                    launchCoroutine {
                        reconnectCartSocketUseCase.execute()
                    }
                }
            }

            is CartEvent.RemoveServiceSelectedEmployee -> {
                removeServiceSelectedEmployee(event.branchServiceId)
            }
            else -> {}
        }
    }

    private fun scrollToTargetDate(date: LocalDate) {
        viewStates?.calendarUIModel?.value?.targetDate?.value = date
//        updateCart(appointmentDate = date.toIsoFormat())
    }

    private fun handleChangedPayment(id: Int) {
        val newSelectedPayment = viewStates?.paymentTypes?.find { it?.uniqueId == id }
        val oldSelectedPayment = viewStates?.paymentTypes?.find { it?.isChecked?.value == true }
        if (newSelectedPayment?.key != oldSelectedPayment?.key) {
            updateCart(paymentMethod = newSelectedPayment?.key)
        }
    }

    private fun changeAddress(customerAddressUIModel: CustomerAddressUIModel?) {
        if (viewStates?.selectedAddress?.value?.uniqueId != customerAddressUIModel?.uniqueId) {
            updateCart(addressId = customerAddressUIModel?.uniqueId) {
                viewStates?.selectedAddress?.value = customerAddressUIModel
            }
        }
    }

    private fun updateNotes(notes: String? = null) {
        val currentNotes = viewStates?.noteUIModel?.value?.note
        if (notes != currentNotes?.value) {
            updateCart(notes = notes?.trim()) {
                currentNotes?.value = notes ?: ""
            }
        }
    }

    private fun updateCart(
        addressId: Int? = null,
        notes: String? = null,
        appointmentLocation: String? = null,
        paymentMethod: String? = null,
        appointmentDate: String? = null,
        firstServiceTime: String? = null,
        onComplete: () -> Unit = {}
    ) {
        val params = listOf(addressId, notes, appointmentLocation, paymentMethod, appointmentDate, firstServiceTime)
        if (params.all { it == null }) return
        launchCoroutine {
            setLoading()
            val updateCartDomainModel = UpdateCartDomainModel(
                paymentMethod = paymentMethod,
                appointmentLocation = appointmentLocation,
                customerAddressId = addressId,
                appointmentDate = appointmentDate,
                firstServiceTime = firstServiceTime,
                notes = notes
            )
            val cart = updateCartUseCase.execute(updateCartDomainModel)
            cart?.let { onComplete() }
            if (cart?.isRescheduling == false) {
//                refreshCart(cart)
                setDoneLoading()
            }
//            cart?.let {
//                refreshCart(cart)
//            }
//            setDoneLoading()
        }
    }

    private fun confirmAppointment() {
        launchCoroutine {
            setLoading()
            val appointment = createAppointmentUseCase.execute()
            setDoneLoading()
            setState { CartState.OpenConfirmationScreen(appointment?.id ?: -1) }
        }
    }

    private fun handleChangedAppointmentLocation(index: Int) {
        val newSelectedTab = viewStates?.availableLocations?.getOrNull(index)
        val newLocation = cartValue?.allowedLocations?.find { it.name == newSelectedTab?.title }
        updateCart(appointmentLocation = newLocation?.key)

    }

    private fun checkCartValidation(validationMessages: List<ValidationMessageUIModel>?) {
//        if (cartValue?.startsAt != null) {
//            if (cartValue?.appointmentLocation == OfferedLocationType.Home) {
//                if (cartValue?.customerAddress != null) {
//                    viewStates?.enableConfirmBtn?.value = true
//                    return
//                }
//            } else {
//                viewStates?.enableConfirmBtn?.value = true
//                return
//            }
//        }

        viewStates?.validationMessage?.value = validationMessages?.firstOrNull()

        viewStates?.enableConfirmBtn?.value = cartValue?.isValid == true
    }

    override fun resetViewStates() {
        viewStates?.isRefreshing?.value = true
        viewStates?.networkError?.value = false
        viewStates?.availableLocations?.clear()
    }

    private fun observeInternetConnection(){

        launchCoroutine {
            notificationManager.notificationFlow.filterIsInstance<NotificationType.NetworkConnection>()
                .collect {
                    setEvent(CartEvent.NetworkConnectionChanged(it.isNetworkConnected.toIntOrNull() == 1))
                }
        }
    }
    override fun updateItem(item: BaseUpdatableItem) {
        when (item) {
            is ServiceDomainModel -> {
                //TODO Remove it After socket
//                getCart()
            }
        }
    }

    private fun removeServiceSelectedEmployee(branchServiceId: Int) {
        launchCoroutine {
            setLoading()
            updateBranchServiceInCartUseCase.execute(branchServiceId = branchServiceId, isAnyone = true)
            setDoneLoading()
        }
    }

    override fun userUpdates(user: UserUIModel) {
        super.userUpdates(user)
        viewStates?.user?.value = user
    }

    override fun onCleared() {
        disconnectGetCartUpdatesUseCase.execute()
        super.onCleared()
    }
}
