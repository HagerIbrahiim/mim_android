package com.trianglz.mimar.modules.cart.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.kizitonwose.calendar.core.atStartOfMonth
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.trianglz.core.presentation.base.BaseActivity
import com.trianglz.core.presentation.extensions.checkPermissionAndTakeAction
import com.trianglz.core.presentation.extensions.getActivity
import com.trianglz.core.presentation.extensions.showMaterialDialog
import com.trianglz.core_compose.presentation.composables.BottomSheetContainerLayout
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.core_compose.presentation.helper.LocalNavController
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.extensions.observeAsState
import com.trianglz.mimar.common.presentation.extensions.showRemoveServiceFromCartConfirmationDialog
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.NavGraphs
import com.trianglz.mimar.modules.addresses.ui.model.CustomerAddressUIModel
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsScreenMode
import com.trianglz.mimar.modules.cart.domain.model.CartDomainModel
import com.trianglz.mimar.modules.cart.presentation.composables.CartScreenContent
import com.trianglz.mimar.modules.cart.presentation.composables.NoteBottomSheet
import com.trianglz.mimar.modules.cart.presentation.contract.CartEvent
import com.trianglz.mimar.modules.cart.presentation.contract.CartState
import com.trianglz.mimar.modules.cart.presentation.contract.CartViewState
import com.trianglz.mimar.modules.cart.presentation.model.CartBottomSheetType
import com.trianglz.mimar.modules.cart.presentation.model.ValidationMessageUIModel
import com.trianglz.mimar.modules.cart_validation_bottom_sheet.presentation.CartValidationBottomSheet
import com.trianglz.mimar.modules.destinations.*
import com.trianglz.mimar.modules.home.presentation.HomeActivity
import com.trianglz.mimar.modules.location_bottom_sheet.presentation.LocationBottomSheet
import com.trianglz.mimar.modules.services.presentation.model.ServiceUIModel
import kotlinx.coroutines.launch
import java.time.*
import java.util.*


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@MainGraph(start = false) // sets this as the start destination of the default nav graph
@Destination
@Composable
fun CartScreen(
    navigator: DestinationsNavigator,
    viewModel: CartViewModel = hiltViewModel(),
    employeeScreenResult: ResultRecipient<EmployeesListScreenDestination, CartDomainModel>,
) {
    val context = LocalContext.current
    val activity = context.getActivity<HomeActivity>()

    /**
    Set the activity soft input mode to "adjustResize" only in this screen to allow the note
    bottom sheet content to flow above the keyboard.
    Disable this mode after leaving the screen, as it can cause bugs.
     */
    //region soft input mode settings
    val lifecycleOwner = LocalLifecycleOwner.current.lifecycle.observeAsState()

    DisposableEffect(key1 = lifecycleOwner.value) {
        when (lifecycleOwner.value) {
            Lifecycle.Event.ON_RESUME -> {
                activity?.window?.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
            else -> {}
        }
        onDispose {
            activity?.window?.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }
    //endregion


    //region Status Bar
    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )

        onDispose {}
    }
    //endregion


    val navController = LocalNavController.current

    val coroutineScope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = { false }
    )

    val viewStates = remember {
        viewModel.viewStates ?: CartViewState()
    }

    val isRefreshing = viewStates.isRefreshing.collectAsState().value
    val showNetworkError = viewStates.networkError.collectAsState().value


    val onChangeAddressClicked = remember {
        {
            viewModel.setEvent(CartEvent.OnChangeAddressClicked)
        }
    }

    val onAddAddressClicked = remember {
        {
            viewModel.setEvent(CartEvent.OnAddAddressClicked)
        }
    }

    val onRemoveServiceClicked: (ServiceUIModel) -> Unit = remember {
        {
            viewModel.setEvent(CartEvent.OnRemoveServiceFromCartClicked(it))
        }
    }

    val onAddAnotherServiceClicked = remember {
        {
            viewModel.setEvent(CartEvent.OnAddAnotherServiceClicked)
        }
    }

    val onConflictClicked: (ValidationMessageUIModel) -> Unit = remember {
        {
            viewModel.setEvent(CartEvent.OnConflictClicked(it))
        }
    }

    val onConfirmAppointmentClicked: () -> Unit = remember {
        {
            viewModel.setEvent(CartEvent.OnConfirmAppointmentClicked)
        }
    }

    val onOpenDiscoverScreenClicked: () -> Unit = remember {
        {
            viewModel.setEvent(CartEvent.OnOpenDiscoverScreenClicked)
        }
    }

    val onCloseBottomSheetClicked = remember {
        {
            viewModel.setEvent(CartEvent.OnCloseBottomSheetClicked)
        }
    }
    val onAddressChangedClicked: (CustomerAddressUIModel) -> Unit = remember {
        {
            viewModel.setEvent(CartEvent.OnAddressChangedClicked(it))
        }
    }

    val onLocationTabChanged: (Int) -> Unit = remember {
        {
            context.apply {
                context.showMaterialDialog(
                    title = getString(R.string.alert),
                    content = getString(R.string.updating_your_preferred_location_may_affect_the_existing_cart),
                    positiveMessage = getString(R.string.continue_msg),
                    negativeMessage = getString(R.string.cancel),
                    positive = {
                        viewModel.setEvent(CartEvent.LocationTabChanged(it))
                    },
                    negative = null
                )

            }

        }
    }


    val onPaymentChanged: (Int) -> Unit = remember {
        {
            viewModel.setEvent(CartEvent.PaymentChanged(it))
        }
    }

    val refreshScreen: () -> Unit = remember {
        {
            viewModel.setEvent(CartEvent.RefreshScreen)
        }
    }

    employeeScreenResult.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }
            is NavResult.Value -> {
                viewModel.setEvent(CartEvent.UpdateCart(result.value))
            }
        }
    }


//    val onCalendarHeaderClicked = remember {
//        {
//            viewModel.setEvent(CartEvent.OnCalendarHeaderClicked)
//        }
//    }
    BottomSheetContainerLayout(
        mainScreenContent = {
//            viewStates.cart.value?.let { cart ->
            CartScreenContent(
                navigator = navigator,
                userModeHandler = viewModel.userModeHandler,
                list = viewStates.list,
                emptyCart = viewStates.emptyCart,
                isRefreshing = { isRefreshing },
                showNetworkError = { showNetworkError },
                onLocationTabChanged = onLocationTabChanged,
                selectedOfferedLocationType = viewStates.selectedOfferedLocationType,
                onChangeAddressClicked = onChangeAddressClicked,
                onConflictClicked = onConflictClicked,
                onAddAnotherServiceClicked = onAddAnotherServiceClicked,
                onConfirmAppointmentClicked = onConfirmAppointmentClicked,
                onAddAddressClicked = onChangeAddressClicked,
                onOpenDiscoverScreenClicked = onOpenDiscoverScreenClicked,
                onPaymentChanged = onPaymentChanged,
                refreshScreen = refreshScreen,
                notificationsCount = viewModel.notificationCount,
                cartCount = viewModel.cartCount
            )
//            }
        },
        bottomSheetContent = {
            when (val type = viewStates.bottomSheetType.value) {
                CartBottomSheetType.Address -> {
                    LocationBottomSheet(
                        addCurrentLocation = false,
                        addNewAddressButtonClicked = onAddAddressClicked,
                        updateDefaultAddress = { false },
                        onLocationSelected = onAddressChangedClicked,
                        hideLocationBottomSheet = onCloseBottomSheetClicked,
                        selectedId = { viewStates.selectedAddress.value?.uniqueId ?: -1 },
                        filterByBranchIdInCart = { true }
//                onLocationSelected = {
//                    closeBottomSheetClicked()
//                }
                    )
                }
                is CartBottomSheetType.ValidationMessage -> {
                    CartValidationBottomSheet(
                        validationMessage = {
                            type.validationMessageUIModel
                        },
                        onClose = onCloseBottomSheetClicked
                    )
                }
                CartBottomSheetType.Note -> {

                    NoteBottomSheet(
                        state = {
                            viewStates.noteBottomSheetState
                        },
                        closeClicked = onCloseBottomSheetClicked,
                        onSubmitBtnClicked = {
                            viewModel.setEvent(CartEvent.SubmitNotesClicked(it))
                        },
                    )
                }
                else -> {
                    Box(Modifier.height(1.dp)) {}
                }
            }
        },
        modifier = Modifier.imePadding(),
        sheetState = sheetState
    )



    GeneralObservers<CartState, CartViewModel>(viewModel = viewModel) {
        when (it) {

            CartState.OpenChangeAddressScreen -> {
//                Toast.makeText(context, "Open Select Address", Toast.LENGTH_SHORT).show()
                coroutineScope.launch {
                    if (!sheetState.isVisible) {
                        viewStates.bottomSheetType.value = CartBottomSheetType.Address
                        sheetState.show()
                    }
                }
            }
            CartState.Finish -> {
                navigator.popBackStack()
            }
            is CartState.SelectDate -> {
                context.getActivity<BaseActivity>()?.showDatePicker(
                    startDate = viewStates.calendarUIModel.value?.startMonth?.atStartOfMonth(),
                    endDate = viewStates.calendarUIModel.value?.endMonth?.atEndOfMonth(),
                    openDate = it.date
                ) { date ->
                    viewModel.setEvent(CartEvent.OnDateChanged(date))
                }
            }
            is CartState.AskToRemoveServiceFromCart -> {
                context.showRemoveServiceFromCartConfirmationDialog {
                    onRemoveServiceClicked(it.serviceUIModel)
                }
            }
            is CartState.ChangeEmployee -> {
                navigator.navigate(
                    EmployeesListScreenDestination(
                        selectedEmployeeId = it.serviceUIModel.assignedEmployee?.takeIf { it.isAnyone == false }?.uniqueId,
                        cartBranchServiceId = it.serviceUIModel.serviceIdInCart,
                        offeredLocation = it.offeredLocation
                    )
                )

            }
            CartState.OpenServicesList -> {
                val cart = viewStates.cart.value
                val branchId = cart?.branchId ?: return@GeneralObservers
                val appointmentLocation = cart.appointmentLocation?.key ?: return@GeneralObservers
                navigator.navigate(
                    AddServiceScreenDestination(
                        branchId,
                        appointmentLocation
                    )
                )
            }
            is CartState.ShowConflictDialog -> {
                coroutineScope.launch {
                    if (!sheetState.isVisible) {
                        viewStates.bottomSheetType.value =
                            CartBottomSheetType.ValidationMessage(it.validationMessage)
                        sheetState.show()
                    }
                }
            }
            is CartState.OpenNoteDialog -> {
                coroutineScope.launch {
                    if (!sheetState.isVisible) {
                        viewStates.bottomSheetType.value = CartBottomSheetType.Note
                        sheetState.show()
                    }
                }
            }
            CartState.CloseBottomSheet -> {
                coroutineScope.launch {
                    viewStates.bottomSheetType.value = null
                    if (sheetState.isVisible) sheetState.hide()
                }
            }
            CartState.OpenAddAddressScreen -> {
                context.checkPermissionAndTakeAction(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    navigator.navigate(
                        MapScreenMainDestination(previousScreenRoute = CartScreenDestination.route)
                    )
                }
//                navigator.navigate(AddAddressScreenMainDestination(fromHome = true))
            }
            CartState.OpenDiscoverScreen -> {
                navigator.navigate(DiscoverScreenDestination(categoryId = 0)) {

                    // Pop up to the root of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(NavGraphs.mainGraph) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }

                if (navController.currentDestination?.route != DiscoverScreenDestination.route) {

                    navigator.navigate(DiscoverScreenDestination(categoryId = 0)) {
                        popUpTo(NavGraphs.mainGraph)
                    }
                }

            }
            is CartState.OpenConfirmationScreen -> {
                navigator.navigate(
                    AppointmentDetailsScreenDestination(
                    it.appointmentId, mode= AppointmentDetailsScreenMode.ConfirmAppointment))
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {}

}

private fun AppCompatActivity.showDatePicker(
    startDate: LocalDate?,
    endDate: LocalDate?,
    openDate: LocalDate?,
    result: (LocalDate) -> Unit
) {
//    val month = date.month.getLong().orNull() ?: System.currentTimeMillis()
    var openCalendarDate = openDate
    if (openDate?.isBefore(startDate) == true) {
        openCalendarDate = startDate
    }

    val openDateLDT = LocalDateTime.of(openCalendarDate ?: LocalDate.now(), LocalTime.now())
    val openDateInMillis =
        openDateLDT.atZone(Clock.systemDefaultZone().zone).toInstant().epochSecond * 1000
    val startDateLDT = LocalDateTime.of(startDate ?: LocalDate.MIN, LocalTime.now())
    val startDateInMillis =
        startDateLDT.atZone(Clock.systemDefaultZone().zone).toInstant().epochSecond * 1000
    val endDateLDT = LocalDateTime.of(endDate ?: LocalDate.MAX, LocalTime.now())
    val endDateInMillis =
        endDateLDT.atZone(Clock.systemDefaultZone().zone).toInstant().epochSecond * 1000

    val constraintsBuilder =
        CalendarConstraints.Builder()
            .setOpenAt(openDateInMillis).setStart(startDateInMillis).setEnd(endDateInMillis).build()
    val picker = MaterialDatePicker.Builder.datePicker().setCalendarConstraints(constraintsBuilder)
        .setTheme(R.style.DatePicker).build()

    picker.show(supportFragmentManager, picker.toString())
    picker.addOnPositiveButtonClickListener {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = it

//        val localDate = LocalDate.ofEpochDay(it)
        val localDateTime = LocalDateTime.ofInstant(utc.toInstant(), ZoneId.systemDefault())
        result.invoke(localDateTime.toLocalDate())
    }
}