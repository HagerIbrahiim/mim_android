package com.trianglz.mimar.modules.home.presentation

import android.app.Application
import android.util.Log
import com.trianglz.core.domain.model.BaseUpdatableItem
import com.trianglz.core.domain.model.StringWrapper
import com.trianglz.core.presentation.extensions.postValue
import com.trianglz.core.presentation.helper.getFcmTokenOrNull
import com.trianglz.core.presentation.model.AsyncState
import com.trianglz.mimar.R
import com.trianglz.mimar.common.presentation.base.GeneralUpdatesViewModel
import com.trianglz.mimar.modules.cart.domain.usecase.ClearCartUseCase
import com.trianglz.mimar.modules.cart.domain.usecase.ConnectGetCartUpdatesUseCase
import com.trianglz.mimar.modules.home.presentation.contracts.HomeActivityEvent
import com.trianglz.mimar.modules.home.presentation.contracts.HomeActivityState
import com.trianglz.mimar.modules.home.presentation.contracts.HomeViewState
import com.trianglz.mimar.modules.notification.domain.manager.ForegroundNotificationManager
import com.trianglz.mimar.modules.payment.domain.model.PaymentType
import com.trianglz.mimar.modules.payment.domain.usecase.EmitPaymentTypeUseCase
import com.trianglz.mimar.modules.services.domain.model.ServiceDomainModel
import com.trianglz.mimar.modules.user.domain.usecase.GetUserUpdatesUseCase
import com.trianglz.mimar.modules.user.domain.usecase.SetUserUseCase
import com.trianglz.mimar.modules.user.presentaion.mapper.toDomain
import com.trianglz.mimar.modules.user.presentaion.model.UserUIModel
import com.trianglz.mimar.modules.usermodehandler.domain.UserModeHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUserUpdatesUseCase: GetUserUpdatesUseCase,
    private val notificationManager: ForegroundNotificationManager,
    private val userModeHandler: UserModeHandler,
    private val clearCartUseCase: ClearCartUseCase,
    private val emitPaymentTypeUseCase: EmitPaymentTypeUseCase,
    private val setUserUseCase: SetUserUseCase,
    private val application: Application,
    ): GeneralUpdatesViewModel<HomeActivityEvent, HomeViewState, HomeActivityState>(getUserUpdatesUseCase) {

    val notificationTypes = notificationManager.notificationFlow.filter { it.isBackground }
    val foregroundNotificationTypes =
        notificationManager.notificationFlow.filter { it.isBackground.not() }

    val userValue get() = viewStates?.user?.value
    init {
        startListenForUserUpdates()
        launchCoroutine {
            Log.d("fcm_token", ": fcm = ${getFcmTokenOrNull()}")
            userModeHandler.getOnGuestRejectedEvents().collect {
                setState { HomeActivityState.ShowGuestDialog }
            }
        }
        launchCoroutine {
            userModeHandler.showAddServiceToCartValidationDialogEvents().collect {pair ->
                setState { HomeActivityState.ShowAddToCartValidationDialog(pair.first, pair.second) }
            }
        }
    }

    override fun handleEvents(event: HomeActivityEvent) {
        when(event) {
            is HomeActivityEvent.ClearCartAndAddNewService -> {
                clearCartAndAddNewService(event.serviceId)
            }
            HomeActivityEvent.UpdateUser -> {
//                TODO()
            }
            is HomeActivityEvent.EmitRequestPaymentStatus -> {
                emitRequestPaymentStatus(event.checkoutId)
            }
            HomeActivityEvent.CartClicked -> {
                setState { HomeActivityState.OpenCart }
            }
            is HomeActivityEvent.AppointmentClickedEvent -> {
                setState { HomeActivityState.OpenAppointment(event.id) }
            }
            is HomeActivityEvent.SubmitAppointmentReviewClicked -> {
                setState { HomeActivityState.OpenSubmitAppointmentReview(event.id) }
            }
            is HomeActivityEvent.ShowDeleteAccountErrorMsg -> {
                if(event.showMessage == true) {
                    Log.d("TAG", "handleEvents: show message")
                    launchCoroutine {
                        delay(500)
                        _loadingState.postValue(AsyncState.SuccessWithMessage(StringWrapper(R.string.account_deleted_successfully)))
                    }
                }
            }
            HomeActivityEvent.UpdateNotificationCount -> {
                launchCoroutine {
                    userValue?.toDomain(application.baseContext)?.copy(unseenNotificationsCount = 1)
                        ?.let { setUserUseCase.execute(user = it, false) }
                }
            }

        }
//        TODO("Not yet implemented")
    }

    private fun clearCartAndAddNewService(serviceId: Int) {
        launchCoroutine {
            setLoading()
            clearCart()
            setDoneLoading()
            addServiceToCart(serviceId = serviceId)
        }
    }

    private suspend fun clearCart() {
        clearCartUseCase.execute()
    }

    private fun addServiceToCart(serviceId: Int) {
        toggleUpdatableItem(ServiceDomainModel(id = serviceId), false)
    }

    private fun emitRequestPaymentStatus(checkoutId: String?) {
        checkoutId?.let {
            launchCoroutine {
                setLoading()
                emitPaymentTypeUseCase.execute(PaymentType.RequestPaymentStatus(checkoutId))
                setDoneLoading()
            }
        }
    }

    override fun createInitialState(): HomeActivityState {
        return HomeActivityState.Idle
    }

    override fun updateItem(item: BaseUpdatableItem) {
//        TODO("Not yet implemented")
    }

    override fun createInitialViewState(): HomeViewState? {
        return HomeViewState()
    }

    override fun userUpdates(user: UserUIModel) {
        super.userUpdates(user)
        viewStates?.user?.value = user
    }
}