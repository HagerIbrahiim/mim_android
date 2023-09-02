package com.trianglz.mimar.modules.appointment_details.presentation

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.base.BaseMVIViewModel
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.model.AsyncState
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.base.MimarBaseViewModel
import com.trianglz.mimar.modules.appointment_details.domain.model.AppointmentDetailsDomainModel
import com.trianglz.mimar.modules.appointment_details.domain.usecase.GetAppointmentCancellationPolicyUseCase
import com.trianglz.mimar.modules.appointment_details.domain.usecase.GetAppointmentDetailsUseCase
import com.trianglz.mimar.modules.appointment_details.domain.usecase.GetAppointmentProblemReasonListUseCase
import com.trianglz.mimar.modules.appointment_details.domain.usecase.UpdateAppointmentUseCase
import com.trianglz.mimar.modules.appointment_details.presentation.contract.AppointmentDetailsEvent
import com.trianglz.mimar.modules.appointment_details.presentation.contract.AppointmentDetailsEvent.*
import com.trianglz.mimar.modules.appointment_details.presentation.contract.AppointmentDetailsState
import com.trianglz.mimar.modules.appointment_details.presentation.contract.AppointmentDetailsState.*
import com.trianglz.mimar.modules.appointment_details.presentation.contract.AppointmentDetailsViewState
import com.trianglz.mimar.modules.appointment_details.presentation.mapper.toUI
import com.trianglz.mimar.modules.appointment_details.presentation.model.*
import com.trianglz.mimar.modules.appointments.domain.model.AppointmentStatusType
import com.trianglz.mimar.modules.branches.domain.model.BranchStatusType
import com.trianglz.mimar.modules.branches.domain.model.BranchStatusType.Companion.toBranchStatusType
import com.trianglz.mimar.modules.branches.domain.usecase.GetBranchDetailsUseCase
import com.trianglz.mimar.modules.destinations.AppointmentDetailsScreenDestination
import com.trianglz.mimar.modules.employee.presentation.model.EmployeeUIModel
import com.trianglz.mimar.modules.filter.presenation.model.BaseCheckboxItemUiModel
import com.trianglz.mimar.modules.filter.presenation.model.BaseSelectionUiModel
import com.trianglz.mimar.modules.filter.presenation.model.SelectionType
import com.trianglz.mimar.modules.notification.domain.manager.ForegroundNotificationManager
import com.trianglz.mimar.modules.notification.domain.model.NotificationType
import com.trianglz.mimar.modules.payment.domain.model.PaymentStatusType
import com.trianglz.mimar.modules.payment.domain.model.getOnlinePaymentMethodType
import com.trianglz.mimar.modules.payment.domain.model.toPaymentMethodType
import com.trianglz.mimar.modules.payment.domain.usecase.GetCheckoutIdUseCase
import com.trianglz.mimar.modules.payment.domain.usecase.GetPaymentStatusUseCase
import com.trianglz.mimar.modules.payment.domain.usecase.GetRequestPaymentStatusUpdatesUseCase
import com.trianglz.mimar.modules.payment.presentation.model.PaymentCheckoutInfoUIModel
import com.trianglz.mimar.modules.payment.presentation.model.PaymentMethodUIModel
import com.trianglz.mimar.modules.payment.presentation.model.PaymentOptionUIModel
import com.trianglz.mimar.modules.ratings.presenation.model.ReviewAppointmentSheetData
import com.trianglz.mimar.modules.services.domain.model.ServiceStatus
import com.trianglz.mimar.modules.services.domain.usecase.UpdateAppointmentServiceUseCase
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import javax.inject.Inject

@HiltViewModel
class AppointmentDetailsViewModel @Inject constructor(
    private val getAppointmentDetailsUseCase: GetAppointmentDetailsUseCase,
    private val updateAppointmentServiceUseCase: UpdateAppointmentServiceUseCase,
    private val updateAppointmentUseCase: UpdateAppointmentUseCase,
    private val getAppointmentProblemReasonListUseCase: GetAppointmentProblemReasonListUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val getCheckoutIdUseCase: GetCheckoutIdUseCase,
    private val getRequestPaymentStatusUpdatesUseCase: GetRequestPaymentStatusUpdatesUseCase,
    private val getPaymentStatusUseCase: GetPaymentStatusUseCase,
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    private val getAppointmentCancellationPolicy: GetAppointmentCancellationPolicyUseCase,
    private val getBranchDetailsUseCase: GetBranchDetailsUseCase,
    notificationManager: ForegroundNotificationManager,
    val userModeHandler: UserModeHandler,
    ) : MimarBaseViewModel<AppointmentDetailsEvent, AppointmentDetailsViewState, AppointmentDetailsState>(getUserUpdatesUseCase) {

    private val appointment get() = viewStates?.appointment?.value
    private val sheetDataItems get() = viewStates?.selectionSheetData?.value?.items
    private val appointmentForegroundNotification =
        notificationManager.notificationFlow.filter { !it.isBackground }
            .filterIsInstance<NotificationType.NotificationTypeWithId.OpenAppointment>()


    private val appointmentExceptionHandler = exceptionHandler {
        viewStates?.isRefreshing?.value = false
        viewStates?.networkError?.value = true
        viewStates?.appointment?.value = null
    }

    private val branchExceptionHandler = exceptionHandler {
        _loadingState.postValue(
            AsyncState.AsyncError.MessageCodeError(
                StringWrapper(R.string.this_branch_is_unavailable)
            )
        )
    }


    init {
        startListenForUserUpdates()
        saveDataFromNavArg()
        observeNotifications()
    }

    private fun observeNotifications() {
        launchCoroutine {
            appointmentForegroundNotification.collect {
                if (it.id == viewStates?.appointmentId && appointment?.mode == AppointmentDetailsScreenMode.AppointmentDetails) {
                    getAppointmentDetails()
                }
            }
        }
    }

    override fun handleEvents(event: AppointmentDetailsEvent) {
        when (event) {

            is EmployeeDidNotShowUpClicked -> {
                viewStates?.reportSheetData?.value =
                    ReportSheetInfo(event.serviceId, event.employeeName){
                        setEvent(OnConfirmReportEmployeeNoShow(event.serviceId))
                    }
                setState { ShowConfirmationBottomSheet(viewStates?.reportSheetData?.value ?: ReportSheetInfo()) }
            }
            RefreshScreen -> {
                getAppointmentDetails()
            }

            BackIconClicked -> {
                setState { FinishScreen }
            }

            PayNowClicked -> {
                getPaymentOptionsList()
            }
            ReportProblemClicked -> {
                getAppointmentProblemReasonsList()
            }
            OnCloseBottomSheetClicked -> {
                viewStates?.isBottomSheetOpened?.value = false
                setState { CloseBottomSheet }
            }
            is SubmitBottomSheetClicked -> {
                handleSubmitBottomSheetOption()
            }
            is OnBottomSheetOptionChecked -> {
                handleSelectedBottomSheetOption(event.option)
            }
            CartIconClicked -> {
                setState { OpenCart }
            }
            CancelAppointmentBtnClicked -> {
                setState { ShowConfirmationBottomSheet(CancelAppointmentUIModel {
                    updateAppointment(status = AppointmentStatusType.Cancelled.key)
                }) }
            }
            ReviewBtnClicked -> {
                getReviewAppointmentSheetData()
            }
            ChangePaymentMethodClicked -> {
                getPaymentMethodList()
            }
            is OnConfirmReportEmployeeNoShow -> {
                updateAppointmentService(serviceId = event.serviceId, employeeDidNotShow = true)
            }
            is BranchCardClicked -> {
                handleBranchClickAction(event.branchId)
            }
            CancellationPolicyClicked -> {
                getCancellationPolicy()
            }
            GotItClicked -> {
                setState { OpenHome }
            }

            is CheckArgsIfChanged -> {
                saveDataFromNavArg(event.bundle)
            }
        }
    }

    private fun saveDataFromNavArg(bundle: Bundle? = null) {
        Log.d("test_notifications_review", "saveDataFromNavArg: saveDataFromNavArg and bundle = $bundle")
        kotlin.runCatching {

            val args =
                if (bundle != null) AppointmentDetailsScreenDestination.argsFrom(bundle) else AppointmentDetailsScreenDestination.argsFrom(
                    savedStateHandle
                )
            args.let {
                if (viewStates?.appointmentId != it.appointmentId) {
                    viewStates?.appointmentId = it.appointmentId
                    getAppointmentDetails()
                    getPaymentStatusUpdates()
                }
                if (it.openReviewAppointment) {
                    viewStates?.openReviewAppointment = true
                }
                bundle?.putBoolean(AppointmentDetailsNavArgs.OPEN_REVIEW_APPOINTMENT, false)
                viewStates?.mode = it.mode
                if (viewStates?.openReviewAppointment == true) {
                    if (appointment != null && appointment?.showShimmer == false) {
                        viewStates?.openReviewAppointment = false
                        getReviewAppointmentSheetData()
                    }
                }
            }
        }
    }

    private fun getAppointmentDetails() {
        viewStates?.appointmentId?.let {
            resetViewStates()
            launchCoroutine(appointmentExceptionHandler) {
                viewStates?.appointment?.value = AppointmentDetailsUIModel.getShimmer()
                val appointment = getAppointmentDetailsUseCase.execute(it)
                viewStates?.appointment?.value = appointment.toUIWithClickActions()
            }
        }
    }


    private fun updateAppointmentService(
        serviceId: Int,
        employeeDidNotShow: Boolean? = null,
    ) {
        launchCoroutine {
            setLoading()
            val appointment =
                updateAppointmentServiceUseCase.execute(
                    serviceId,
                    employeeDidNotShow,
                )
            viewStates?.appointment?.value = appointment.toUIWithClickActions()
            setDoneLoading()
        }

    }

    private fun updateAppointment(
        status: String? = null,
        paymentMethod: String? = null,
        appointmentProblemReasonId: Int? = null,
    ) {
        appointment?.id?.let {
            launchCoroutine {
                setLoading()
                val appointment = updateAppointmentUseCase.execute(
                    it,
                    status, paymentMethod, appointmentProblemReasonId
                )
                viewStates?.appointment?.value = appointment.toUIWithClickActions()
                setDoneLoading()
            }
        }


    }

    private fun AppointmentDetailsDomainModel.toUIWithClickActions() =
        toUI(
            mode = viewStates?.mode ?: AppointmentDetailsScreenMode.AppointmentDetails,
            reportEmployeeDidNotShowClicked = { service ->
                service.serviceIdInCart?.let {
                    setEvent(
                        EmployeeDidNotShowUpClicked(
                            it,
                            service.assignedEmployee?.userName ?: ""
                        )
                    )
                }
            },
            onReportProblemClicked = { setEvent(ReportProblemClicked) },
            payNowClicked = { setEvent(PayNowClicked) },
            reviewBtnClicked = { setEvent(ReviewBtnClicked) },
            cancelAppointmentClicked = { setEvent(CancelAppointmentBtnClicked) },
            changePaymentMethod = { setEvent(ChangePaymentMethodClicked) },
            onBranchClick = {
                setEvent(BranchCardClicked(it))
            },
            cancellationPolicyClicked = {
                setEvent(CancellationPolicyClicked)
            },
            gotItClicked = {
                setEvent(GotItClicked)
            }

        )


    private fun getAppointmentProblemReasonsList() {
        launchCoroutine {
            setLoading()
            val list = getAppointmentProblemReasonListUseCase.execute().map { it.toUI() }
            val reasonsSnapShot = SnapshotStateList<AppointmentProblemReasonsUIModel>()

            viewStates?.selectionSheetData?.value = BaseSelectionUiModel(
                title = StringWrapper(R.string.Select_report_reason),
                items = list.toCollection(reasonsSnapShot),
                selectionType = SelectionType.SingleSelection
            )
            setDoneLoading()
            if (list.isNotEmpty()) setState { OpenSelectionDialog }
        }
    }

    private fun handleSelectedBottomSheetOption(option: BaseCheckboxItemUiModel) {
        val clickedItem = sheetDataItems?.find { it.id == option.id }
        if (clickedItem?.isChecked?.value == false) {
            sheetDataItems?.firstOrNull { it.isChecked.value }?.isChecked?.value =
                false
        }
        clickedItem?.isChecked?.value = !(clickedItem?.isChecked?.value ?: false)
    }

    private fun handleSubmitBottomSheetOption() {
        setState { CloseBottomSheet }
        when (val selectedItem = sheetDataItems?.firstOrNull { it.isChecked.value }) {
            is AppointmentProblemReasonsUIModel -> {
                updateAppointment(appointmentProblemReasonId = selectedItem.id)
            }

            is PaymentMethodUIModel -> {
                if (selectedItem.key != appointment?.paymentMethod?.key) {
                    updateAppointment(paymentMethod = selectedItem.key)
                }
            }

            is PaymentOptionUIModel -> {
                selectedItem.key?.let { requestCheckOutId(it) }
            }
        }
    }

    private fun getPaymentMethodList() {
        launchCoroutine {
            setLoading()
            val list = appointment?.allowedPaymentMethods ?: listOf()
            val paymentMethodsSnapShot = SnapshotStateList<PaymentMethodUIModel>()
            list.firstOrNull {
                it.key == appointment?.paymentMethod?.key
            }?.isChecked?.value = true

            viewStates?.selectionSheetData?.value = BaseSelectionUiModel(
                title = StringWrapper(R.string.payment),
                items = list.toCollection(paymentMethodsSnapShot),
                selectionType = SelectionType.SingleSelection
            )
            setDoneLoading()
            if (list.isNotEmpty()) setState { OpenSelectionDialog }
        }
    }

    private fun getPaymentOptionsList() {
        launchCoroutine {
            setLoading()
            val list = getOnlinePaymentMethodType().mapIndexed { index, payment ->
                PaymentOptionUIModel(
                    id = index,
                    title = StringWrapper(payment.displayName),
                    key = payment.key,
                )
            }
            val paymentMethodsSnapShot = SnapshotStateList<PaymentOptionUIModel>()

            viewStates?.selectionSheetData?.value = BaseSelectionUiModel(
                title = StringWrapper(R.string.payment),
                items = list.toCollection(paymentMethodsSnapShot),
                selectionType = SelectionType.SingleSelection
            )
            setDoneLoading()
            if (list.isNotEmpty()) setState { OpenSelectionDialog }
        }
    }

    private fun handleBranchClickAction(branchId: Int) {
        launchCoroutine(branchExceptionHandler) {
            appointment?.branch?.id?.let {
                val branchStatus = getBranchDetailsUseCase.execute(it).status
                if (branchStatus is BranchStatusType.Active)
                    setState { OpenBranchDetails(branchId) }

            }

        }

    }

    private fun requestCheckOutId(paymentOption: String) {
        launchCoroutine {
            setLoading()
            delay(200)
            setState { CloseBottomSheet }
            val checkoutIdModel = getCheckoutIdUseCase.execute(
                appointment?.id ?: -1, paymentOption
            )

            checkoutIdModel?.checkoutId?.let { checkoutId ->
                setState {
                    OpenPaymentGateway(
                        PaymentCheckoutInfoUIModel(
                            checkoutId,
                            null,
                            paymentOption.toPaymentMethodType()
                        )
                    )

                }

                setDoneLoading()
            }
        }
    }

    private fun getPaymentStatusUpdates() {
        launchCoroutine {
            getRequestPaymentStatusUpdatesUseCase.execute().collect { checkout ->
                if (appointment?.mode == AppointmentDetailsScreenMode.AppointmentDetails) {
                    setLoading()
                    appointment?.id?.let {
                        val appointment = getPaymentStatusUseCase.execute(
                            checkout.checkoutId,
                            it
                        ).appointment?.toUIWithClickActions()
                        viewStates?.appointment?.value = appointment
                    }
                    setDoneLoading()
                    if(appointment?.paymentStatus == PaymentStatusType.Paid) {
                        _loadingState.postValue(AsyncState.SuccessWithMessage(StringWrapper(R.string.payment_done)))
                    }
                }
            }
        }
    }


    private fun getReviewAppointmentSheetData() {
        if (appointment?.hasReview?.not() == true) {
            val employeesList = appointment?.appointmentBranchServices
                ?.filter { it.status is ServiceStatus.Done }
                ?.map { it.assignedEmployee ?: EmployeeUIModel(it.id ?: -1) }
                ?.filter { it.isDeleted == false }
                .orEmpty()

            viewStates?.isBottomSheetOpened?.value = true
            viewStates?.appointmentFeedbackTextFieldState?.textFieldValue?.value = TextFieldValue("")
            viewStates?.reviewAppointmentSheetData?.value = ReviewAppointmentSheetData(
                appointment?.id ?: -1,
                appointment?.branch?.name ?: "",
                employeesList
            )
            setState {
                OpenSubmitAppointmentReview(
                    viewStates?.reviewAppointmentSheetData?.value ?: ReviewAppointmentSheetData()
                )
            }
        }
    }

    private fun getCancellationPolicy(){
        launchCoroutine {
            setLoading()
            val policy = getAppointmentCancellationPolicy.execute()
            setDoneLoading()
            setState { OpenCancellationPolicy(policy) }
        }
    }
    override fun resetViewStates() {
        viewStates?.networkError?.value = false
    }

    override fun createInitialViewState() = AppointmentDetailsViewState()

}

