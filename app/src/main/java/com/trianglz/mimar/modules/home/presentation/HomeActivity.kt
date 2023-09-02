package com.trianglz.mimar.modules.home.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.enums.MessageDialogMode
import com.trianglz.core.presentation.extensions.collectWhenCreated
import com.trianglz.core.presentation.extensions.isNetworkConnected
import com.trianglz.core.presentation.extensions.toActivityAsNewTask
import com.trianglz.core.presentation.extensions.toActivityAsNewTaskWithParams
import com.trianglz.core.presentation.livedata.Event
import com.trianglz.core.presentation.model.AsyncState
import com.trianglz.core_compose.presentation.base.BaseComposeMVIActivity
import com.trianglz.core_compose.presentation.compose_ui.BaseComposeMainUIComponents.Companion.LocalMainComponent
import com.trianglz.core_compose.presentation.helper.GeneralObservers
import com.trianglz.core_compose.presentation.helper.LocalNavController
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.composables.MessageDialog
import com.trianglz.mimar.common.presentation.extensions.isInternetAvailable
import com.trianglz.mimar.common.presentation.extensions.listenToInternetConnectivity
import com.trianglz.mimar.common.presentation.extensions.showAddToCartValidationDialog
import com.trianglz.mimar.common.presentation.extensions.showGuestUserDialog
import com.trianglz.mimar.common.presentation.navigation.BottomBar
import com.trianglz.mimar.modules.NavGraphs
import com.trianglz.mimar.modules.appointment_details.presentation.model.AppointmentDetailsScreenMode
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationActivity
import com.trianglz.mimar.modules.authentication.presentation.AuthenticationMode
import com.trianglz.mimar.modules.destinations.AppointmentDetailsScreenDestination
import com.trianglz.mimar.modules.destinations.CartScreenDestination
import com.trianglz.mimar.modules.home.presentation.contracts.HomeActivityEvent
import com.trianglz.mimar.modules.home.presentation.contracts.HomeActivityState
import com.trianglz.mimar.modules.notification.domain.manager.ForegroundNotificationManager
import com.trianglz.mimar.modules.notification.domain.model.NotificationType
import com.trianglz.mimar.modules.notification.presentation.service.MimarMessagingService
import com.trianglz.mimar.modules.payment.presentation.PaymentManagerHandler
import com.trianglz.mimar.modules.payment.presentation.model.PaymentCheckoutInfoUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import com.trianglz.core.R as coreR

@AndroidEntryPoint
class HomeActivity: BaseComposeMVIActivity<HomeViewModel>() {

    companion object {
        const val SHOW_DELETE_ACCOUNT_MSG = "delete_account_msg"
        const val INTERNET_CONNECTION = "internet_connection"
    }
    @Inject
    lateinit var notificationManager: ForegroundNotificationManager

    @Inject
    lateinit var userModeHandler: UserModeHandler

    private val paymentObserver by lazy {
        PaymentManagerHandler(
            activityResultRegistry,
            this,
            this::emitRequestPaymentStatus,
            this::paymentDone,
            this::paymentError,
        )
    }

    private fun emitRequestPaymentStatus(checkoutId:String){
        viewModel.setEvent(HomeActivityEvent.EmitRequestPaymentStatus(checkoutId))
    }

    private fun paymentDone(){
        //TODO
    }

    private fun paymentError(error: String) {
        globalState.updateValue(
            Event(
                AsyncState.AsyncError.MessageCodeError(
                    StringWrapper(if(isInternetAvailable())
                        com.trianglz.core.R.string.something_went_wrong
                    else com.trianglz.core.R.string.no_connection)
                )
            )
        )
    }

    override fun initializeViewModel() {
        initializeViewModel(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intObservers()
        notificationObservers()
        lifecycle.addObserver(paymentObserver)

        listenToInternetConnectivity {
            /**
             * This is an exceptional case where we use notification flow to enable internet
             * connection checks throughout the app.
             * Using notification flow allows us to accomplish this task since it can be
             * accessed from any ViewModel within the app.
             */
            val intent = Intent()
            intent.apply {
                val actionId = if(it) 1 else 0
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtras(
                    bundleOf(
                        Pair(
                            ForegroundNotificationManager.ACTION_ID, actionId.toString()
                        ),
                        Pair(
                            ForegroundNotificationManager.CLICK_ACTION, INTERNET_CONNECTION
                        )
                    )
                )
                notificationManager.emitNotifications(this)
            }
        }
    }


    private fun notificationObservers() {
        collectWhenCreated(viewModel.notificationTypes) {

            when (it) {
                is NotificationType.OpenCart -> {
                    viewModel.setEvent(HomeActivityEvent.CartClicked)
                }
                is NotificationType.NotificationTypeWithId.OpenAppointment -> {
                    viewModel.setEvent(HomeActivityEvent.AppointmentClickedEvent(it.id))
                }
                is NotificationType.NotificationTypeWithId.SubmitAppointmentReview -> {
                    viewModel.setEvent(HomeActivityEvent.SubmitAppointmentReviewClicked(it.id))
                }
                else -> Unit
            }
        }

        collectWhenCreated(viewModel.foregroundNotificationTypes) {
            when (it) {
                else -> Unit
            }
        }
    }

    private fun handleShowingAccountErrorMessage(){
        val showDeleteMessage = intent?.getBooleanExtra(SHOW_DELETE_ACCOUNT_MSG, false)
        viewModel.setEvent(HomeActivityEvent.ShowDeleteAccountErrorMsg(showDeleteMessage))
    }

    private fun intObservers(){
//        observeEvent(viewModel.loadingState){
//            Log.d("TAG", "onCreate: async state $it ")
//            globalState.postValue(it)
//        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("ForegroundNotificationManager, checkNewIntent: ${intent?.extras?.getString("action_id")}")
        val isBackground =
            intent?.getBooleanExtra(MimarMessagingService.FOREGROUND_KEY, false)?.not() ?: true
        notificationManager.emitNotifications(intent, isBackground)

        if (isBackground.not()) {
            viewModel.setEvent(HomeActivityEvent.UpdateNotificationCount)
        }

        handlePaymentCallback(intent)
    }

    private fun handlePaymentCallback(intent: Intent?) {
        if (intent?.scheme == getString(R.string.mimar_payment_callback_scheme)) {
            Log.d("test_payment", "onNewIntent: ${intent.data}")
            intent.data?.getQueryParameter("id")?.let { checkoutId ->
                emitRequestPaymentStatus(checkoutId)
            }

            // request payment status if ActivityResultCallback is received
        }
    }

    private fun checkIntent() {
        Timber.d("ForegroundNotificationManager, checkIntent: ${intent.extras?.getString("action_id")}")
        notificationManager.emitNotifications(intent, true)
    }

    override fun handleStates() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.filterNotNull().collect {
                    when (it) {
                        HomeActivityState.Idle -> {
                            checkIntent()
                            handleShowingAccountErrorMessage()
                        }
                        HomeActivityState.OpenLogin -> {
                            toActivityAsNewTask<AuthenticationActivity>()
                        }
                        HomeActivityState.ShowGuestDialog -> {
                            showGuestUserDialog {
                                val list = listOf(
                                    Pair(AuthenticationActivity.SCREEN_MODE, AuthenticationMode.Register)
                                )
                                toActivityAsNewTaskWithParams<AuthenticationActivity>(*list.toTypedArray())
                            }
                        }
                        is HomeActivityState.ShowAddToCartValidationDialog -> {
                            showAddToCartValidationDialog(it.branchName) {
                                viewModel.setEvent(HomeActivityEvent.ClearCartAndAddNewService(it.newServiceId))
                            }
                        }
                        else -> {return@collect}
                    }
                    super.handleStates()

                }
            }
        }

    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    override fun InitializeComposeScreen() {
        CompositionLocalProvider(LocalNavController provides rememberAnimatedNavController()) {
            val engine = rememberAnimatedNavHostEngine()
            val navController = LocalNavController.current

            Scaffold(
                bottomBar = {
                    BottomBar(navController = navController, false,
                        userModeHandler)
                }
                //...
            ) {
                DestinationsNavHost(
                    engine = engine,
                    navController = navController,
                    navGraph = NavGraphs.mainGraph,
                    startRoute = NavGraphs.mainGraph.startRoute
                )
            }
            GeneralObservers<HomeActivityState, HomeViewModel>(viewModel = viewModel) { state ->
                when (state) {
                    is HomeActivityState.OpenAppointment -> {
                        navController.navigate(
                            AppointmentDetailsScreenDestination(
                                appointmentId = state.id, mode = AppointmentDetailsScreenMode.AppointmentDetails
                            ),
                            navOptionsBuilder = {
                                launchSingleTop = true
                            }
                        )
                    }
                    HomeActivityState.OpenCart -> {
                        navController.navigate(
                            direction = CartScreenDestination(),
                            navOptionsBuilder = {
                                launchSingleTop = true
                            }
                        )
                    }
                    is HomeActivityState.OpenSubmitAppointmentReview -> {
                        navController.navigate(
                            AppointmentDetailsScreenDestination(
                                appointmentId = state.id,
                                mode = AppointmentDetailsScreenMode.AppointmentDetails,
                                openReviewAppointment = true
                            ),
                            navOptionsBuilder = {
                                launchSingleTop = true
                            },
                        )
                    }
                    else -> {

                    }
                }
            }
        }

    }

    @Composable
    override fun CustomLoadingDialog() {
        LocalMainComponent.LoadingDialog(lottieFile = R.raw.loader)
    }

    @Composable
    override fun CustomAlertDialog(message: String, mode: MessageDialogMode) {
        MessageDialog({mode},{ message }, dismiss = { alertDialog.dismiss() })

    }

    fun openPaymentGateway(checkoutInfoUIModel: PaymentCheckoutInfoUIModel) {
        paymentObserver.handleOpenCheckoutActivity(checkoutInfoUIModel)
    }

}