package com.trianglz.mimar.modules.appointment_details.presentation

import android.view.WindowManager
import com.trianglz.mimar.R
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.trianglz.core.domain.helper.getBoolean
import com.trianglz.core.presentation.extensions.getActivity
import com.trianglz.core_compose.presentation.composables.BottomSheetContainerLayout
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.HandleLoadingStateObserver
import com.trianglz.core_compose.presentation.helper.LocalNavController
import com.trianglz.mimar.common.presentation.extensions.observeAsState
import com.trianglz.mimar.common.presentation.navigation.MainGraph
import com.trianglz.mimar.modules.NavGraphs
import com.trianglz.mimar.modules.appointment_details.presentation.composables.AppointmentDetailsScreenContent
import com.trianglz.mimar.modules.appointment_details.presentation.composables.bottom_sheets.CancellationPolicyBottomSheet
import com.trianglz.mimar.modules.appointment_details.presentation.composables.bottom_sheets.ConfirmationBottomSheet
import com.trianglz.mimar.modules.appointment_details.presentation.contract.AppointmentDetailsEvent
import com.trianglz.mimar.modules.appointment_details.presentation.contract.AppointmentDetailsState
import com.trianglz.mimar.modules.appointment_details.presentation.contract.AppointmentDetailsViewState
import com.trianglz.mimar.modules.appointment_details.presentation.model.*
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsBottomSheetType.*
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsNavArgs.Companion.APPOINTMENT_ID
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsNavArgs.Companion.OPEN_REVIEW_APPOINTMENT
import com.trianglz.mimar.modules.destinations.BranchDetailsScreenDestination
import com.trianglz.mimar.modules.destinations.CartScreenDestination
import com.trianglz.mimar.modules.destinations.UserHomeScreenDestination
import com.trianglz.mimar.modules.filter.presenation.composables.SelectionBottomSheet
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.home.presentation.HomeActivity
import com.trianglz.mimar.modules.ratings.presenation.composables.appointment_review.ReviewAppointmentBottomSheet
import com.trianglz.mimar.modules.ratings.presenation.model.ReviewAppointmentSheetData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@MainGraph(start = false)
@Destination(navArgsDelegate = AppointmentDetailsNavArgs::class)
@Composable
fun AppointmentDetailsScreen(
    viewModel: AppointmentDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val arguments = LocalNavController.current.currentBackStackEntry?.arguments
    //region Status Bar
    val systemUiController = rememberSystemUiController()
    val context = LocalContext.current
    val activity = context.getActivity<HomeActivity>()

    DisposableEffect(systemUiController) {

        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )

        onDispose {}
    }
    //endregion

    //region soft input mode settings
    val lifecycleOwner = LocalLifecycleOwner.current.lifecycle.observeAsState()

    DisposableEffect(key1 = lifecycleOwner.value) {
        when (lifecycleOwner.value) {
            Lifecycle.Event.ON_RESUME -> {
                activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }
            else -> {}
        }
        onDispose {
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }
    }
    //endregion

//    LaunchedEffect(key1 = viewModel.viewStates?.appointmentId == arguments?.getInt(APPOINTMENT_ID) , key2 = arguments?.getBoolean(OPEN_REVIEW_APPOINTMENT, false) == viewModel.viewStates?.openReviewAppointment) {
        arguments?.let {
            viewModel.setEvent(AppointmentDetailsEvent.CheckArgsIfChanged(arguments))
        }
//    }

    val viewStates = remember {
        viewModel.viewStates ?: AppointmentDetailsViewState()
    }

    val appointment by remember {
        viewStates.appointment
    }


    val selectionSheetData by remember {
        viewStates.selectionSheetData
    }

    val bottomSheetType by remember {
        viewStates.bottomSheetType
    }

    val isBottomSheetOpened by remember {
        viewStates.isBottomSheetOpened
    }

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = { false }
    )

    val showNetworkError = viewStates.networkError.collectAsState()
    val isRefreshing = viewStates.isRefreshing.collectAsState()

    val onRefreshScreen = remember {
        {
            viewModel.setEvent(AppointmentDetailsEvent.RefreshScreen)
        }
    }

    val backIconClicked = remember {
        {
            viewModel.setEvent(AppointmentDetailsEvent.BackIconClicked)
        }
    }

    val cartIconClicked = remember {
        {
            viewModel.setEvent(AppointmentDetailsEvent.CartIconClicked)
        }
    }


    val onCloseBottomSheetClicked = remember {
        {
            viewModel.setEvent(AppointmentDetailsEvent.OnCloseBottomSheetClicked)
        }
    }

    val onBottomSheetSubmitClicked = remember {
        {
            viewModel.setEvent(AppointmentDetailsEvent.SubmitBottomSheetClicked)
        }
    }

    val onBottomSheetOptionChecked: (BaseCheckboxItemUiModel) -> Unit = remember {
        {
            viewModel.setEvent(AppointmentDetailsEvent.OnBottomSheetOptionChecked(it))
        }
    }


    BackHandler {
            viewModel.setEvent(
                if(viewStates.mode is AppointmentDetailsScreenMode.AppointmentDetails)
                    AppointmentDetailsEvent.BackIconClicked
            else AppointmentDetailsEvent.GotItClicked)
    }

    val coroutineScope = rememberCoroutineScope()

    BottomSheetContainerLayout(
        mainScreenContent = {
            AppointmentDetailsScreenContent(
                navigator = navigator,
                appointmentDetails = { appointment },
                showNetworkError = { showNetworkError.value },
                isRefreshing = { isRefreshing.value },
                notificationsCount = viewModel.notificationCount,
                cartCount = viewModel.cartCount,
                userModeHandler = viewModel.userModeHandler,
                onSwipeToRefresh = onRefreshScreen,
                onBackBtnClicked = backIconClicked,
                onCartIconClicked = cartIconClicked,
            )

        },
        bottomSheetContent = {
            when (bottomSheetType) {
                is SingleSelection -> {
                    SelectionBottomSheet(
                        isButtonEnabled = {
                            selectionSheetData?.items?.any { it.isChecked.value } ?: false
                        },
                        buttonText = {R.string.submit},
                        selectionItem = { selectionSheetData },
                        onSubmitBtnClicked = onBottomSheetSubmitClicked,
                        onBackButtonClicked = onCloseBottomSheetClicked,
                        onOptionChecked = onBottomSheetOptionChecked
                    )
                }
                is SubmitAppointmentReview -> {
                    ReviewAppointmentBottomSheet(
                        isBottomSheetOpened = { isBottomSheetOpened } ,
                        onCloseBottomSheetClicked = onCloseBottomSheetClicked,
                        refreshAppointment = onRefreshScreen,
                        feedback = { viewStates.appointmentFeedbackTextFieldState },
                        data = { viewStates.reviewAppointmentSheetData.value ?: ReviewAppointmentSheetData() }
                    )
                }
                is ConfirmationMessage  ->{
                    val data = (bottomSheetType as ConfirmationMessage).dataModel
                    ConfirmationBottomSheet(
                        confirmationDialogType = { data?.confirmationDialogType ?: ConfirmationDialogType.CancelAppointment},
                        okBtnClicked = data?.confirmationDialogOkClicked ?: {},
                        onCloseBottomSheetClicked = onCloseBottomSheetClicked
                    )
                }

                is CancellationPolicy ->{
                    CancellationPolicyBottomSheet(
                        message = { (bottomSheetType as CancellationPolicy).policy },
                        onBackButtonClicked = onCloseBottomSheetClicked
                    )
                }

                else -> {
                    Box(Modifier.height(1.dp)) {}
                }
            }

        },
        sheetState = sheetState,
        modifier = Modifier.imePadding(),
    )
    val navController = LocalNavController.current

    GeneralObservers<AppointmentDetailsState, AppointmentDetailsViewModel>(viewModel = viewModel) {
        when (it) {

            AppointmentDetailsState.FinishScreen -> {
                navigator.popBackStack()
            }

            AppointmentDetailsState.OpenSelectionDialog -> {
                coroutineScope.launch {
                    if (!sheetState.isVisible) {
                        viewStates.bottomSheetType.value = SingleSelection
                        sheetState.show()
                    }
                }
            }

            AppointmentDetailsState.CloseBottomSheet -> {
                coroutineScope.launch {
                    viewStates.bottomSheetType.value = null
                    if (sheetState.isVisible) sheetState.hide()
                }
            }
            AppointmentDetailsState.OpenCart -> {
                navigator.navigate(CartScreenDestination)
            }
            is AppointmentDetailsState.OpenSubmitAppointmentReview -> {
                coroutineScope.launch {
                    if (!sheetState.isVisible) {
                        viewStates.bottomSheetType.value = SubmitAppointmentReview(it.sheetData)
                        sheetState.show()
                    }
                }
            }

            is AppointmentDetailsState.OpenBranchDetails -> {
                navigator.navigate(BranchDetailsScreenDestination(it.branchId))
            }
            AppointmentDetailsState.OpenHome -> {
                navController.navigate(UserHomeScreenDestination.route) {
                    // Pop up to the root of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(NavGraphs.mainGraph) {
                        //saveState = true
                    }

                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                if (navController.currentDestination?.route != UserHomeScreenDestination.route) {
                    navigator.navigate(UserHomeScreenDestination.route)
                }

            }
            is AppointmentDetailsState.OpenPaymentGateway -> {
                context.getActivity<HomeActivity>()?.apply {
                    openPaymentGateway(it.paymentCheckoutInfoUIModel)
                }
            }
            is AppointmentDetailsState.ShowConfirmationBottomSheet -> {
                coroutineScope.launch {
                    if (!sheetState.isVisible) {
                        viewStates.bottomSheetType.value = ConfirmationMessage(it.data)
                        sheetState.show()
                    }
                }
            }
            is AppointmentDetailsState.OpenCancellationPolicy -> {
                coroutineScope.launch {
                    if (!sheetState.isVisible) {
                        viewStates.bottomSheetType.value = CancellationPolicy(it.policy)
                        sheetState.show()
                    }
                }
            }
        }
    }

    HandleLoadingStateObserver(viewModel = viewModel) {}


}